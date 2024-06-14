package com.cn.studentportal.controller;

import java.util.List;

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

import com.cn.studentportal.bean.EmailBean;
import com.cn.studentportal.bean.StudentBean;
import com.cn.studentportal.dto.DashBoardDto;
import com.cn.studentportal.dto.PageDto;
import com.cn.studentportal.dto.StudentDto;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.service.StudentService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/student")
@Slf4j
@CrossOrigin("*")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody StudentBean student) {
		log.info("Saving Student entity {}", student.getStdName());
		try {

			studentService.save(student);

			log.info("Saving Student entity is done");
			return ResponseEntity.status(HttpStatus.OK).body("{\"msg\": \"Student saved Successfully\"}");
		} catch (RecordAlreadyExistsException e) {
			log.error("Student deleting failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error : " + e.getMessage() + "}");
		} catch (Exception e) {
			log.error("Error occurred while saving student {} ", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error : " + e.getMessage() + "}");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<StudentBean> get(@PathVariable int id) {
		log.info("Retrieving Student by Id {} ", id);
		try {
			StudentBean studentBean = studentService.getById(id);
			log.info("Retrived student by id is successfully");
			return ResponseEntity.status(HttpStatus.OK).body(studentBean);
		} catch (Exception e) {
			log.error("Error occurred while retrieving student", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/all")
	public ResponseEntity<List<StudentBean>> get() {
		log.info("Retrieving All Studnets ");
		try {
			List<StudentBean> list = studentService.getAll();
			ResponseEntity<List<StudentBean>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
			log.info("Fetching All Student details is done");
			return responseEntity;
		} catch (Exception e) {
			log.error("Error occurred while retrieving all studnets", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/alldtos")
	public ResponseEntity<List<StudentDto>> getDtos() {
		log.info("Retrieving All Studnets ");
		try {
			List<StudentDto> list = studentService.getAllDtos();
			log.info("Fetching All Student details is done");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			log.error("Error occurred while retrieving all studnets", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/alldtos/{page}/{size}")
	public ResponseEntity<PageDto<List<StudentDto>,Integer>> getAllByPage(@PathVariable int page, @PathVariable int size) {
		log.info("Retrieving All Studnets ");
		try {
			PageDto<List<StudentDto>,Integer> list = studentService.getAllDtosByPage(page, size);
			log.info("Fetching All Student details is done");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			log.error("Error occurred while retrieving all studnets", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/count")
	public ResponseEntity<List<DashBoardDto>> getDashboardDtos() {
		log.info("Retrieving All Studnets ");
		try {
			List<DashBoardDto> list = studentService.getDashboardDtos();
			log.info("Fetching All Student details is done");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			log.error("Error occurred while retrieving all studnets", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		log.info("Deleting Student by Id {} ", id);
		try {
			studentService.delete(id);

			log.info("Deleted Student by Id successfully");
			return ResponseEntity.status(HttpStatus.OK).body("{\"msg\": \"student deleted sucessfully\"}");
		} catch (RecordNotFoundException e) {
			log.error("Student deleting failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error : " + e.getMessage() + "}");
		} catch (Exception e) {
			log.error("Error occurred while deleting student", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating student\"}");
		}
	}

	@PutMapping
	public ResponseEntity<String> put(@RequestBody StudentDto studentDto) throws Exception {

		log.info("Updating Student {} ", studentDto.getStdId());
		try {

			studentService.update(studentDto);

			log.info("Updating student is done");
			return ResponseEntity.status(HttpStatus.OK).body("{\"msg\": \"" + "Updated Student Successfully.." + "\"}");
		} catch (RecordNotFoundException e) {
			log.error("Student updating failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error : " + e.getMessage() + "}");
		} catch (Exception e) {
			log.error("Error occurred while updating student", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating Student\"}");
		}
	}

	@GetMapping("/referName/{referedName}/{page}/{size}")
	public ResponseEntity<PageDto<List<StudentDto>,Integer>> getStudentsByReferedName(@PathVariable String referedName,
			@PathVariable int page, @PathVariable int size) {

		log.info("Retrieving All Studnets ");
		try {

			PageDto<List<StudentDto>,Integer> list = studentService.getAllStudentsByReferedName(referedName, page, size);
			log.info("Fetching All Student details is done");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (RecordNotFoundException e) {
			log.error("Error occurred while retrieving all studnets", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/stdName/{stdName}/{page}/{size}")
	public ResponseEntity<PageDto<List<StudentDto>,Integer>> getStudentsByStdName(@PathVariable String stdName,
			@PathVariable int page, @PathVariable int size) {

		log.info("Retrieving All Studnets ");
		try {

			PageDto<List<StudentDto>,Integer> list = studentService.getAllStudentsByStdName(stdName, page, size);
			log.info("Fetching All Student details is done");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (RecordNotFoundException e) {
			log.error("Error occurred while retrieving all studnets", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@GetMapping("/filter/{startDate}/{endDate}")
	public ResponseEntity<List<StudentDto>> getStudentsBetweenDates(@PathVariable java.sql.Date startDate,
	        @PathVariable java.sql.Date endDate) {
	    try {
	        log.info("Retrieving All Students ");
	        
	        
	        List<StudentDto> list = studentService.filterStudentDetailsByDateRange(startDate, endDate);
	        log.info("Fetching All Student details is done");
	        return ResponseEntity.status(HttpStatus.OK).body(list);
	    } catch (Exception e) {
	        log.error("Error retrieving student details: {}", e.getMessage());
	        return new ResponseEntity<List<StudentDto>>(HttpStatus.NOT_FOUND);
	    }
	}
	
	
 	@GetMapping("/excel/{startDate}/{endDate}")
	public void generateExcel(@PathVariable java.sql.Date startDate, @PathVariable java.sql.Date endDate,
			HttpServletResponse response) {

		log.info("Fetching All Student details is done and report is generated");
		response.setContentType("application/vnd.ms-excel");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=courses.xls";

		response.setHeader(headerKey, headerValue);

		studentService.generateExcelReport(startDate, endDate, response);

		try {
			response.flushBuffer();
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@PostMapping("/email")
	public ResponseEntity<String> sendEmail(@RequestBody EmailBean emailBean) {
		log.info("sending email {}", emailBean.getToAddress());
		try {

			studentService.sendEmail(emailBean);

			log.info("sending email is done");
			return ResponseEntity.status(HttpStatus.OK).body("{\"msg\": \"Email sending Successfully\"}");
		} catch (Exception e) {
			log.error("Error occurred while sending email {} ", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error : " + e.getMessage() + "}");
		}
	}


}
