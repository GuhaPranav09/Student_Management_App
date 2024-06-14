package com.cn.studentportal.service;

import java.util.Date;
import java.util.List;

import com.cn.studentportal.bean.EmailBean;
import com.cn.studentportal.bean.StudentBean;
import com.cn.studentportal.dto.DashBoardDto;
import com.cn.studentportal.dto.PageDto;
import com.cn.studentportal.dto.StudentDto;
import com.cn.studentportal.entity.Student;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;

public interface StudentService {

	StudentBean save(StudentBean student)
			throws RecordAlreadyExistsException, RecordNotFoundException, MessagingException;

	StudentBean getById(int id) throws RecordNotFoundException;

	List<StudentBean> getAll();

	void delete(int id) throws RecordNotFoundException;

	Student update(StudentDto studentDto) throws RecordNotFoundException;

	List<StudentDto> getAllDtos();

	PageDto<List<StudentDto>, Integer> getAllDtosByPage(int page, int size);

	PageDto<List<StudentDto>, Integer> getAllStudentsByReferedName(String referedName, int page, int size)
			throws RecordNotFoundException;

	List<DashBoardDto> getDashboardDtos();

	PageDto<List<StudentDto>, Integer> getAllStudentsByStdName(String stdName, int page, int size)
			throws RecordNotFoundException;

	List<StudentDto> filterStudentDetailsByDateRange(Date startDate, Date endDate) throws RecordNotFoundException;

	String generateExcelReport(Date startDate, Date endDate, HttpServletResponse response);

	EmailBean sendEmail(EmailBean emailBean) throws MessagingException;

}
