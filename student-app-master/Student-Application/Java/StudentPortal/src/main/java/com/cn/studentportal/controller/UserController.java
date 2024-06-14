package com.cn.studentportal.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.studentportal.bean.LoginBean;
import com.cn.studentportal.bean.UserBean;
import com.cn.studentportal.dto.PageDto;
import com.cn.studentportal.entity.User;
import com.cn.studentportal.exception.EmailNotFoundException;
import com.cn.studentportal.exception.InvalidOtpException;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.service.UserService;
import com.cn.studentportal.util.CommonConstants;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;
	private static Logger log = LoggerFactory.getLogger(UserController.class.getSimpleName());

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody UserBean user) throws RecordAlreadyExistsException {
		log.info("Saving User entity {}", user.getUserEmail());
		try {
			user.setCmnStatusId(CommonConstants.Active);
			 userService.save(user);
			log.info("Saving User entity is done");
			return ResponseEntity.status(HttpStatus.CREATED).body("{\"msg\": \"" + "Saved User Scucessfully..."+ "\"}");
		} catch (RecordAlreadyExistsException exception) {
			log.error("Error occured while saving user : {} ", exception.getMessage());
			//throw exception; 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + "User Email already Existed"+ "\"}");
		}catch (Exception e) {
			log.error("Error occurred while saving user : {} ", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + "Something Went Wrong"+ "\"}");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserBean> get(@PathVariable int id) {
		log.info("Retrieving User by Id {} ", id);
		try {
			UserBean userBean = userService.getById(id);
			log.info("Retrived user by id is successfully");
			return ResponseEntity.status(HttpStatus.OK).body(userBean);
		} catch (Exception e) {
			log.error("Error occurred while retrieving user", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/all/{page}/{size}")
	public ResponseEntity<PageDto<List<UserBean>,Integer>> get(@PathVariable int page, @PathVariable int size) {
		log.info("Retrieving All Users ");
		try {
			PageDto<List<UserBean>,Integer> list= userService.getAll(page,size);
			log.info("Fetching All User details is done");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			log.error("Error occurred while retrieving all users", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
//	
//	@GetMapping("/alldtos/{page}/{size}")
//	public ResponseEntity<PageDto<List<StudentDto>,Integer>> getAllByPage(@PathVariable int page, @PathVariable int size) {
//		log.info("Retrieving All Studnets ");
//		try {
//			PageDto<List<StudentDto>,Integer> list = studentService.getAllDtosByPage(page, size);
//			log.info("Fetching All Student details is done");
//			return ResponseEntity.status(HttpStatus.OK).body(list);
//		} catch (Exception e) {
//			log.error("Error occurred while retrieving all studnets", e.getMessage());
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		log.info("Deleting User by Id  {} ", id);
		try {
			userService.delete(id);
			log.info("Deleted User by Id successfully");
			 return ResponseEntity.status(HttpStatus.OK).body("{\"msg\": \"" + "Deleted User Scucessfully..."+ "\"}");
		} catch (RecordNotFoundException e) {
			log.error("User deleting failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			log.error("Error occurred while deleting user", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating stram\"}");
		}
	}

	@PutMapping
	public ResponseEntity<String> put(@RequestBody UserBean user) throws Exception {

		log.info("Updating User {} ", user.getUserId());
		try {
			userService.update(user);
			log.info("Updating user is done");
			return ResponseEntity.status(HttpStatus.OK).body("{\"msg\": \"" + "Updated User Successfully.."+ "\"}");
		} catch (RecordNotFoundException e) {
			log.error("User updating failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			log.error("Error occurred while updating user", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating User\"}");
		}
	}

	@PutMapping("/updatestatus")
	public void updateByUser(@RequestBody User user) {
		log.info("Update the  user status ");

		userService.delete(user);
		log.info("Updating user status is done");
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<UserBean> login(@RequestBody LoginBean loginBean) {
//		log.info("login User entity {}", loginBean.getEmail() );
//		try {
//			UserBean userBean = userService.login(loginBean);
//			log.info(" User login successful.");
//			return ResponseEntity.status(HttpStatus.OK).body(userBean);
//		} catch (RecordNotFoundException exception) {
//			log.error("Error occured while fetching user by eamil and password : {} ", exception);
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}catch (Exception e) {
//			log.error("Error  while login user", e);
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@GetMapping("/generateotp/{email}")
	public ResponseEntity<User> generateOtpAndSendEmail(@PathVariable("email") String email) {

		try {
			log.info("Generate otp by using email");
			User user = userService.forgetPassword(email);
			if (user != null) {
				log.info("Generate otp by using email is done");
				return new ResponseEntity<User>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
			}

		} catch (EmailNotFoundException e) {

			log.error("email id is not valid");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);

		}
	}

	@GetMapping("/verify/{email}/{enteredOtp}")
	public ResponseEntity<String> verifyOtp(@PathVariable String email, @PathVariable String enteredOtp) {
		try {
			log.info("verify the otp by using email");
			if (userService.verifyOtp(email, enteredOtp)) {
				String jsonString = "{\"message\":\"Verified Successfully\"}";
				log.info("verify the  otp by using email is done");

				return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(jsonString);
			} else {
				log.info("Sending  the invalid otp");
				String jsonString = "{\"message\":\"Invalid OTP\"}";
				System.out.println("jsonString" + jsonString);

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json")
						.body(jsonString);
			}
		} catch (InvalidOtpException e) {
			String jsonString = "{\"message\":\"wrong otp\"}";
			log.error("error handled");

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json")
					.body(jsonString);

		}
	}
	
	@GetMapping("/updatepassword/{email}/{newPassword}")
	public ResponseEntity<User> updatePassword(@PathVariable String email, @PathVariable String newPassword) {
		log.info("Update Password");
		User user = userService.updatePassword(email, newPassword);
		if (user != null) {
			log.info("Update Password by using email is done");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/searchbyname/{name}/{page}/{size}")
	public ResponseEntity<PageDto<List<User>,Integer>> getUsersByName( @PathVariable String name,@PathVariable int page, @PathVariable int size) {
		log.info("Update Password");
		PageDto<List<User>,Integer>  user = userService.getUsersByName(name,page,size);
		if (user != null) {
			log.info("Update Password by using email is done");
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} else {
			return new ResponseEntity<PageDto<List<User>,Integer>> (HttpStatus.NOT_FOUND);
		}
	}
	
//	@GetMapping("/referName/{referedName}/{page}/{size}")
//	public ResponseEntity<PageDto<List<StudentDto>,Integer>> getStudentsByReferedName(@PathVariable String referedName,
//			@PathVariable int page, @PathVariable int size) {
//
//		log.info("Retrieving All Studnets ");
//		try {
//
//			PageDto<List<StudentDto>,Integer> list = studentService.getAllStudentsByReferedName(referedName, page, size);
//			log.info("Fetching All Student details is done");
//			return ResponseEntity.status(HttpStatus.OK).body(list);
//		} catch (RecordNotFoundException e) {
//			log.error("Error occurred while retrieving all studnets", e.getMessage());
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	
}
