package com.cn.studentportal.service;

import java.util.List;

import com.cn.studentportal.bean.StreamBean;
import com.cn.studentportal.entity.Stream;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;

public interface StreamService {
	StreamBean save(StreamBean stream) throws RecordAlreadyExistsException;

	StreamBean getById(int id) throws RecordNotFoundException;

	List<StreamBean> getAll();

	void delete(int id) throws RecordNotFoundException;

	void delete(Stream stream);

	StreamBean update(StreamBean streamBean) throws RecordNotFoundException;

}
