package com.cn.studentportal.service;


import java.util.List;

import com.cn.studentportal.bean.StdStatusBean;
import com.cn.studentportal.entity.StdStatus;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;

public interface StdStatusService {
	StdStatusBean save(StdStatusBean stdStatus) throws RecordAlreadyExistsException;

	StdStatusBean getById(String id) throws RecordNotFoundException;

	List<StdStatusBean> getAll();

	void delete(String id) throws RecordNotFoundException;

	void delete(StdStatus stdStatus);

	StdStatusBean update(StdStatusBean stdStatusBean) throws RecordNotFoundException;
}
