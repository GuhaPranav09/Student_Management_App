package com.cn.studentportal.service;

import java.util.List;

import com.cn.studentportal.bean.LoginBean;
import com.cn.studentportal.bean.UserBean;
import com.cn.studentportal.dto.PageDto;
import com.cn.studentportal.entity.User;
import com.cn.studentportal.exception.EmailNotFoundException;
import com.cn.studentportal.exception.InvalidOtpException;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;

public interface UserService {
	UserBean save(UserBean user) throws RecordAlreadyExistsException;

	UserBean getById(int id) throws RecordNotFoundException;

	List<UserBean> getAll();

	void delete(int id) throws RecordNotFoundException;

	void delete(User user);

	UserBean update(UserBean user) throws RecordNotFoundException;

	UserBean login(LoginBean loginBean) throws RecordNotFoundException;

	User forgetPassword(String email) throws EmailNotFoundException;

	boolean verifyOtp(String email, String enteredOtp) throws InvalidOtpException;

	User updatePassword(String email, String newPassword);

//	List<User> getUsersByName(String name);

//	PageDto<List<UserBean>, Integer> getAll(int pageNo, int pageSize);
	public PageDto<List<User>, Integer> getUsersByName(String name, int page, int size);

	PageDto<List<UserBean>, Integer> getAll(int pageNo, int pageSize);
}
