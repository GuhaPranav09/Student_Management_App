package com.cn.studentportal.serviceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cn.studentportal.bean.EmailBean;
import com.cn.studentportal.bean.StudentBean;
import com.cn.studentportal.dto.DashBoardDto;
import com.cn.studentportal.dto.PageDto;
import com.cn.studentportal.dto.StudentDto;
import com.cn.studentportal.entity.Student;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.repository.StudentRepository;
import com.cn.studentportal.service.StudentService;
import com.cn.studentportal.util.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ObjectMapper objectMapper;

	@Value("${app.download.filepath}")
	private String downLoadFilePath;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public StudentBean save(StudentBean studentBean)
			throws RecordAlreadyExistsException, RecordNotFoundException, MessagingException {

		try {
			log.info("Saving the student"); 
			Student student = new Student();
			Student findedStudent = studentRepository.getByEmail(studentBean.getEmail());

			if (findedStudent == null) {
				student = beanToEntity(studentBean);
				student.setStdStatusId(CommonConstants.REGISTERD);
				student.setDateOfJoin(new Date());
				student.setStatus(CommonConstants.NO);
				Student dbStudent = studentRepository.save(student);

				StudentBean dbStreamBean = entityToBean(dbStudent);

				return dbStreamBean;
			} else {
				throw new RecordAlreadyExistsException("Student already existed with given Email ");
			}
		} catch (RecordAlreadyExistsException exception) {
			log.error("Error occured while saving student : {}", exception.getMessage());
			throw exception;
		}

	}

	@Override
	public Student update(StudentDto studentDto) throws RecordNotFoundException {

		try {
			log.info("Saving the student");

			studentRepository.findById(studentDto.getStdId())
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id :  "));

			Student student = stdDtoToStdEntity(studentDto);

			Student dbStudent = studentRepository.save(student);

			return dbStudent;

		} catch (RecordNotFoundException exception) {
			log.error("Error occured while updating student", exception.getMessage());
			throw exception;
		}
	}

	@Override
	public StudentBean getById(int id) throws RecordNotFoundException {

		try {
			log.info("Retrieving student by id");
			Student student = studentRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id : " + id));

			StudentBean studentBean = entityToBean(student);
			return studentBean;
		} catch (RecordNotFoundException exception) {
			log.error("Error occured while fetching student by id : {}", exception.getMessage());
			throw exception;
		}
	}

	@Override
	public List<StudentBean> getAll() {

		try {
			log.info("Retrieving all students");
			List<Student> list = studentRepository.findAll();

			List<StudentBean> beanList = entityToBean(list);
			return beanList;
		} catch (Exception exception) {
			log.error("Error occured while fetching all students : {}", exception.getMessage());
			throw exception;
		}

	}

	@Override
	public List<StudentDto> getAllDtos() {

		try {
			log.info("Retrieving all students");
			return studentRepository.findAllDtos();
		} catch (Exception exception) {
			log.error("Error occured while fetching all students : {} ", exception.getMessage());
			throw exception;
		}

	}

	@Override
	public PageDto<List<StudentDto>, Integer> getAllDtosByPage(int page, int size) {

		try {
			log.info("Retrieving all students");
			Pageable pageable = PageRequest.of(page, size);
			Page<StudentDto> stdDtoPage = studentRepository.findAllDtosByPage(pageable);

			PageDto<List<StudentDto>, Integer> pageDto = PageDto.<List<StudentDto>, Integer>builder()
					.pageNo(stdDtoPage.getNumber()).pageSize(stdDtoPage.getSize()).last(stdDtoPage.isLast())
					.first(stdDtoPage.isFirst()).totalPages(stdDtoPage.getTotalPages())
					.totalRecords(stdDtoPage.getTotalElements()).records(stdDtoPage.getContent()).build();
			pageDto.setCount(getCount());
			return pageDto;
		} catch (Exception exception) {
			log.error("Error occured while fetching all students : {} ", exception.getMessage());
			throw exception;
		}

	}

	public Integer getCount() {

		return studentRepository.getStudentsCount();

	}

	@Override
	public List<DashBoardDto> getDashboardDtos() {
		try {
			log.info("Retrieving all DashboardDtos");
			return studentRepository.findAllDashBoardDtos();
		} catch (Exception exception) {
			log.error("Error occured while fetching all DashboardDtos : {} ", exception.getMessage());
			throw exception;
		}
	}

	@Override
	public void delete(int id) throws RecordNotFoundException {
		try {
			log.info("Deleting student by id {} ", id);

			studentRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));

			studentRepository.deleteById(id);

		} catch (RecordNotFoundException exception) {
			log.error("Error occured while deleting student by id : {}", exception.getMessage());
			throw exception;
		}

	}

	public Student beanToEntity(StudentBean studentBean) {

		Student student = objectMapper.convertValue(studentBean, Student.class);
		return student;
	}

	public StudentBean entityToBean(Student student) {
		StudentBean studentBean = objectMapper.convertValue(student, StudentBean.class);
		return studentBean;
	}

	public List<StudentBean> entityToBean(List<Student> list) {
		List<StudentBean> beanList = new ArrayList<>();
		for (Student student : list) {
			StudentBean studentBean = new StudentBean();
			studentBean = objectMapper.convertValue(student, StudentBean.class);
			beanList.add(studentBean);
		}
		return beanList;
	}

	public Student stdDtoToStdEntity(StudentDto studentDto) {
		Student student = Student.builder().stdId(studentDto.getStdId()).stdName(studentDto.getStdName())
				.yop(studentDto.getYop()).streamId(studentDto.getStreamId())
				.specializationId(studentDto.getSpecializationId()).qualPercentage(studentDto.getQualPercentage())
				.contactNo(studentDto.getContactNo()).email(studentDto.getEmail()).referedBy(studentDto.getReferedBy())
				.address(studentDto.getAddress()).stdStatusId(studentDto.getStdStatusId())
				.amountPaid(studentDto.getAmountPaid()).interestedInId(studentDto.getInterestedInId())
				.dateOfJoin(studentDto.getDateOfJoin()).build();
		return student;
	}

	@Override
	public PageDto<List<StudentDto>, Integer> getAllStudentsByReferedName(String referedName, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<StudentDto> stdList = studentRepository.getStudentsByReferedName(referedName, pageable);
		PageDto<List<StudentDto>, Integer> pageDto = PageDto.<List<StudentDto>, Integer>builder()
				.pageNo(stdList.getNumber()).pageSize(stdList.getSize()).last(stdList.isLast()).first(stdList.isFirst())
				.totalPages(stdList.getTotalPages()).totalRecords(stdList.getTotalElements())
				.records(stdList.getContent()).build();
		pageDto.setCount(stdList.getContent().size());
		return pageDto;
	}

	@Override
	public PageDto<List<StudentDto>, Integer> getAllStudentsByStdName(String stdName, int page, int size)
			throws RecordNotFoundException {

		Pageable pageable = PageRequest.of(page, size);
		Page<StudentDto> stdList = studentRepository.getStudentsByStdName(stdName, pageable);

		PageDto<List<StudentDto>, Integer> pageDto = PageDto.<List<StudentDto>, Integer>builder()
				.pageNo(stdList.getNumber()).pageSize(stdList.getSize()).last(stdList.isLast()).first(stdList.isFirst())
				.totalPages(stdList.getTotalPages()).totalRecords(stdList.getTotalElements())
				.records(stdList.getContent()).build();
		pageDto.setCount(stdList.getContent().size());

		return pageDto;
	}

	@Override
	public List<StudentDto> filterStudentDetailsByDateRange(Date startDate, Date endDate)
			throws RecordNotFoundException {
		if (startDate != null && endDate != null) {
			return studentRepository.getStudentDetailsBetweenTheDates(startDate, endDate);

		} else {
			throw new RecordNotFoundException("records not found");
		}

	}

	private void sendEmailToStudent(String toEmail, String studentName) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(toEmail);
		helper.setSubject("Congratulations! Registration successful");
		helper.setText(createRegistrationEmail(studentName), true);
		javaMailSender.send(message);
	}

	private String createRegistrationEmail(String studentName) {
		return "Dear " + studentName + ",<br><br>" + "Greetings!!!!!<br><br>"
				+ "We are happy to inform you that you have been registered with Fingrow consultancy services successfully.<br><br>"
				+ "Your job placement will take 90 days duration once you complete the below process.<br><br>"
				+ "1. Java and SQL training<br>" + "2. Mock interviews<br>" + "3. Group discussion<br><br>"
				+ "For any meetings, please find the below zoom link to attend:<br><br>"
				+ "https://us06web.zoom.us/j/4331965796?pwd=cesRnJ2Uv5ZjXJmRA4AVeQuxJAyHge.1<br><br>"
				+ "Meeting ID: <b>433 196 5796</b><br>" + "Passcode: <b>NNEX82</b><br><br>"
				+ "Note: Registration amount will not be refunded if you don't attend the above process.<br>"
				+ "We will keep posting the updates regarding your job.<br><br>" + "All the best......!<br><br>"
				+ "Best Regards,<br>" + "Fingrow consultancies.";
	}

	@Override
	public String generateExcelReport(Date startDate, Date endDate, HttpServletResponse response) {
		List<StudentDto> listOfStudents = studentRepository.getStudentDetailsBetweenTheDates(startDate, endDate);
		log.info("started generating Excel report");
		try (Workbook book = new XSSFWorkbook()) {

			Sheet sheet = book.createSheet("Report_from_" + startDate + "_" + endDate);

			int rowNum = 1;
			Row row = sheet.createRow(0);
			int cellCount = 0;
			createCellWithStyle(book, row, cellCount++, "Name");
			createCellWithStyle(book, row, cellCount++, "Year Of Pass");
			createCellWithStyle(book, row, cellCount++, "Stream");
			createCellWithStyle(book, row, cellCount++, "Specialization");
			createCellWithStyle(book, row, cellCount++, "Qualification");
			createCellWithStyle(book, row, cellCount++, "Phone No");
			createCellWithStyle(book, row, cellCount++, "Email Address");
			createCellWithStyle(book, row, cellCount++, "Reffered By");
			createCellWithStyle(book, row, cellCount++, "Date Of Joining");
			createCellWithStyle(book, row, cellCount++, "Amount Paid");

			for (StudentDto stdDto : listOfStudents) {

				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				String dateOfJoining = formatter.format(stdDto.getDateOfJoin());
				cellCount = 0;
				row = sheet.createRow(rowNum++);
				row.createCell(cellCount++).setCellValue(stdDto.getStdName());
				row.createCell(cellCount++).setCellValue(stdDto.getYop());
				row.createCell(cellCount++).setCellValue(stdDto.getStreamName());
				row.createCell(cellCount++).setCellValue(stdDto.getSpecializationName());
				row.createCell(cellCount++).setCellValue(stdDto.getQualPercentage());
				row.createCell(cellCount++).setCellValue(stdDto.getContactNo());
				row.createCell(cellCount++).setCellValue(stdDto.getEmail());
				row.createCell(cellCount++).setCellValue(stdDto.getReferedBy());
				row.createCell(cellCount++).setCellValue(dateOfJoining);
				if(stdDto.getAmountPaid() != null) {
				row.createCell(cellCount++).setCellValue(Double.valueOf(stdDto.getAmountPaid().doubleValue()));
				}
				else
				{
					row.createCell(cellCount++).setCellValue(Double.valueOf("0"));
				}
				
			}
			ServletOutputStream ops = response.getOutputStream();
			book.write(ops);
			book.close();
			ops.close();
			log.info("Fetching All Student details is done and report is generated ");
		} catch (IOException e) {

			e.printStackTrace();
		}
		log.info("Completed Excel report download");
		return "successfully created";
	}

	@Override
	public EmailBean sendEmail(EmailBean emailBean) throws MessagingException {
		log.info("Method entry for sending Email ");
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			// String[] toAddressArr = emailBean.getToAddress().split(";");

			helper.setTo(emailBean.getToAddress().split(";"));

			if (StringUtils.isNotBlank(emailBean.getCcAddress())) {
				helper.setCc(emailBean.getCcAddress().split(";"));
			}
			helper.setSubject(emailBean.getEmailsubject());
			helper.setText(emailBean.getEmailBody());
			javaMailSender.send(message);
			log.info("Method exit for sending Email ");
			return emailBean;
		} catch (Exception e) {
			log.error("Error while sending email ");
			throw new MessagingException("Failed to send email", e);
		}

	}

	@Scheduled(fixedRate = 120000)
	public void sendMail() {
		log.info("Method entry for sending Emails using Scheduler ");
		List<Student> students = studentRepository.getStudentsWhomNeedtoSendMail();
		System.out.println(students);
		students.stream().forEach(student -> {
			try {
				sendEmailToStudent(student.getEmail(), student.getStdName());
				student.setStatus(CommonConstants.YES);
				studentRepository.save(student);
			} catch (MessagingException e) {
				log.error("Error while sending email ");
				e.printStackTrace();
			}
		});
		log.info("Method Exit for sending Emails using Scheduler ");
	}

	private void createCellWithStyle(Workbook book, Row row, int cellIndex, String rowHeader) {
		CellStyle headerCellStyle = book.createCellStyle();
		Font headerFont = book.createFont();
		headerFont.setBold(true);
		headerCellStyle.setFont(headerFont);

		row.createCell(cellIndex).setCellValue(rowHeader);
		row.getCell(cellIndex).setCellStyle(headerCellStyle);
	}
}
