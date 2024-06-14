package com.cn.studentportal.serviceImpl;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cn.studentportal.bean.LoginBean;
import com.cn.studentportal.bean.UserBean;
import com.cn.studentportal.dto.PageDto;
import com.cn.studentportal.dto.StudentDto;
import com.cn.studentportal.entity.Otp;
import com.cn.studentportal.entity.User;
import com.cn.studentportal.exception.EmailNotFoundException;
import com.cn.studentportal.exception.InvalidOtpException;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.repository.OtpRepository;
import com.cn.studentportal.repository.UserRepository;
import com.cn.studentportal.service.UserService;
import com.cn.studentportal.util.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private JavaMailSender javaMailSender;

//	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class.getSimpleName());

	@Override
	public UserBean save(UserBean userBean) throws RecordAlreadyExistsException {

		try {
			log.info("Saving the user"); 
			User user = new User();
			Optional<User> findedUser = userRepository.findByUserEmail(userBean.getUserEmail());

			if (findedUser.isEmpty()) {
				user = beanToEntity(userBean);
				user.setCmnStatusId(CommonConstants.Active);
				if (StringUtils.isEmpty(userBean.getIsAdmin())) {
					user.setIsAdmin(CommonConstants.NO);
				}
				if (StringUtils.isEmpty(userBean.getUserPassword())) {
					user.setUserPassword(passwordEncoder.encode(userBean.getUserContact()));
				} else {
					user.setUserPassword(passwordEncoder.encode(userBean.getUserPassword()));
				}
				User dbUser = userRepository.save(user);

				UserBean dbUserBean = entityToBean(dbUser);
				return dbUserBean;
			} else {
				throw new RecordAlreadyExistsException("User already existed with given Email ");
			}
		} catch (RecordAlreadyExistsException exception) {
			log.error("Error occured while saving user : {} ", exception.getMessage());
			throw exception;
		}

	}

	@Override
	public UserBean update(UserBean userBean) throws RecordNotFoundException {
		try {
			log.info("Saving the user");

			User user = userRepository.findById(userBean.getUserId())
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));
			String password = user.getPassword();
			userBean.setUserPassword(password);
			user = beanToEntity(userBean);
			User dbUser = userRepository.save(user);

			UserBean dbUserBean = entityToBean(dbUser);

			return dbUserBean;

		} catch (RecordNotFoundException exception) {
			log.error("Error occured while updating user", exception);
			throw exception;
		}

	}

	@Override
	public UserBean getById(int id) throws RecordNotFoundException {

		try {
			log.info("Retrieving user by id");
			User user = userRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));

			UserBean userBean = entityToBean(user);
			return userBean;
		} catch (RecordNotFoundException exception) {
			log.error("Error occured while fetching user by id", exception);
			throw exception;
		}
	}

	@Override
	public List<UserBean> getAll() {

		try {
			log.info("Retrieving all users");
			Sort sort = Sort.by(Sort.Order.asc("cmnStatusId"), Sort.Order.desc("userId"));

			List<User> list = userRepository.findAll(sort);

			List<UserBean> beanList = entityToBean(list);
			return beanList;
		} catch (Exception exception) {
			log.error("Error occured while fetching all users", exception);
			throw exception;
		}

	}

	@Override
	public PageDto<List<UserBean>, Integer> getAll(int pageNo, int pageSize) {
		try {
			log.info("Retrieving all users");

			Sort sort = Sort.by(Sort.Order.asc("cmnStatusId"), Sort.Order.desc("userId"));

			Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

			Page<User> userPage = userRepository.findAll(pageable);

			List<UserBean> userBeans = userPage.getContent().stream().map(user -> entityToBean(user))
					.collect(Collectors.toList());

			PageDto<List<UserBean>, Integer> pageDto = PageDto.<List<UserBean>, Integer>builder()
					.pageNo(userPage.getNumber()).pageSize(userPage.getSize()).last(userPage.isLast())
					.first(userPage.isFirst()).totalPages(userPage.getTotalPages())
					.totalRecords(userPage.getTotalElements()).records(userBeans).build();

			pageDto.setCount(getCount());
			System.out.println(pageDto);
			return pageDto;

		} catch (Exception exception) {
			log.error("Error occurred while fetching all users", exception);
			throw exception;
		}
	}

	public Integer getCount() {

		return userRepository.getStudentsCount();

	}

	@Override
	public void delete(int id) throws RecordNotFoundException {
		try {
			log.info("Deleting user by id");
			User user = userRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id : " + id));
			user.setCmnStatusId(CommonConstants.InActive);
			userRepository.save(user);

		} catch (RecordNotFoundException exception) {
			log.error("Error occured while deleting user by id", exception);
			throw exception;
		}

	}

	public String generateOtp() {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}

	public void sendOtpEmail(String toEmail, String otp) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("Your OTP");
		message.setText("Your OTP is: " + otp);
		javaMailSender.send(message);
	}

	@Override
	public User forgetPassword(String email) throws EmailNotFoundException {

		Optional<User> user = userRepository.findByUserEmail(email);

		if (user.isPresent()) {
			log.info("Email is valid");
			String otp = generateOtp();
			System.out.println(user);
			Timestamp expirationTime = Timestamp.from(Instant.now().plus(Duration.ofMinutes(5)));
			sendOtpEmail(email, otp);
			saveOtp(email, otp, expirationTime);
			return user.get();
		} else {
			log.info("Email is not valid");
			throw new EmailNotFoundException("Email not found");
		}

	}

	public void saveOtp(String email, String otp, Timestamp expirationTime) {
		Optional<Otp> existingOtp = otpRepository.findByEmail(email);

		if (existingOtp.isPresent()) {
			existingOtp.get().setOtp(otp);
			existingOtp.get().setExpirationTime(expirationTime);
			otpRepository.save(existingOtp.get());
		} else {
			Otp newOtp = new Otp();
			newOtp.setEmail(email);
			newOtp.setOtp(otp);
			newOtp.setExpirationTime(expirationTime);
			otpRepository.save(newOtp);
		}
	}

	@Override
	public boolean verifyOtp(String email, String enteredOtp) throws InvalidOtpException {

		Otp otpEntity = otpRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("OTP not found"));

		Timestamp expirationTime = otpEntity.getExpirationTime();

		LocalDateTime expirationLocalDateTime = expirationTime.toInstant().atZone(ZoneId.systemDefault())
				.toLocalDateTime();

		if (expirationLocalDateTime.isBefore(LocalDateTime.now())) {
			return false;
		}

		else if (!enteredOtp.equals(otpEntity.getOtp())) {
			throw new InvalidOtpException("Invalid otp");
		} else {
			return true;
		}
	}

	@Override
	public User updatePassword(String email, String newPassword) {
		User user = userRepository.findByUserEmail(email).get();
		user.setUserPassword(passwordEncoder.encode(newPassword));
		return userRepository.save(user);
	}

	public User beanToEntity(UserBean userBean) {

		// User user = objectMapper.convertValue(userBean, User.class);
		User user = User.builder().userId(userBean.getUserId()).name(userBean.getName())
				.userEmail(userBean.getUserEmail()).userContact(userBean.getUserContact())
				.cmnStatusId(userBean.getCmnStatusId()).userPassword(userBean.getUserPassword())
				.isAdmin(userBean.getIsAdmin()).role(userBean.getRole()).build();
		return user;
	}

	public UserBean entityToBean(User user) {
//		UserBean userBean = objectMapper.convertValue(user, UserBean.class);

		UserBean userBean = UserBean.builder().userId(user.getUserId()).name(user.getName())
				.userEmail(user.getUserEmail()).userContact(user.getUserContact()).cmnStatusId(user.getCmnStatusId())
				.isAdmin(user.getIsAdmin()).role(user.getRole()).build();
		return userBean;
	}

	public List<UserBean> entityToBean(List<User> list) {
		List<UserBean> beanList = new ArrayList<>();
		for (User user : list) {
			UserBean userBean = new UserBean();
			userBean = UserBean.builder().userId(user.getUserId()).name(user.getName()).userEmail(user.getUserEmail())
					.userContact(user.getUserContact()).cmnStatusId(user.getCmnStatusId()).isAdmin(user.getIsAdmin())
					.role(user.getRole()).build();
			beanList.add(userBean);
		}

		return beanList;
	}

	@Override
	public void delete(User user) {
		if (user.getCmnStatusId().equalsIgnoreCase(CommonConstants.Active)) {
			user.setCmnStatusId(CommonConstants.InActive);
		} else {
			user.setCmnStatusId(CommonConstants.Active);
		}
		userRepository.save(user);

	}

	@Override
	public UserBean login(LoginBean loginBean) throws RecordNotFoundException {

		try {
			log.info("Retrieving user by id");
			User user = userRepository.findByUserEmailAndUserPassword(loginBean.getEmail(), loginBean.getPassword())
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));

			UserBean userBean = entityToBean(user);
			userBean.setUserPassword(null);
			return userBean;
		} catch (RecordNotFoundException exception) {
			log.error("Error occured while fetching user by eamil and password", exception.getMessage());
			throw exception;
		}

	}

	@Override
	public PageDto<List<User>, Integer> getUsersByName(String name, int page, int size) {
		Sort sort = Sort.by(Sort.Order.asc("cmnStatusId"), Sort.Order.desc("userId"));
		Pageable pageable = PageRequest.of(page, size);
		Page<User> stdList = userRepository.findByUserByName(name, pageable);
		PageDto<List<User>, Integer> pageDto = PageDto.<List<User>, Integer>builder().pageNo(stdList.getNumber())
				.pageSize(stdList.getSize()).last(stdList.isLast()).first(stdList.isFirst())
				.totalPages(stdList.getTotalPages()).totalRecords(stdList.getTotalElements())
				.records(stdList.getContent()).build();
		pageDto.setCount(stdList.getContent().size());
		return pageDto;

//		return userRepository.findByUserByName(name);
	}

//	@Override
//	public PageDto<List<StudentDto>, Integer> getAllStudentsByReferedName(String referedName, int page, int size) {
//		Pageable pageable = PageRequest.of(page, size);
//		Page<StudentDto> stdList = studentRepository.getStudentsByReferedName(referedName, pageable);
//		PageDto<List<StudentDto>, Integer> pageDto = PageDto.<List<StudentDto>, Integer>builder()
//				.pageNo(stdList.getNumber()).pageSize(stdList.getSize()).last(stdList.isLast()).first(stdList.isFirst())
//				.totalPages(stdList.getTotalPages()).totalRecords(stdList.getTotalElements())
//				.records(stdList.getContent()).build();
//		pageDto.setCount(stdList.getContent().size());
//		return pageDto;
//	}

}
