package com.cn.studentportal.service;

import java.util.List;

import com.cn.studentportal.bean.CmnStatusBean;
import com.cn.studentportal.entity.CmnStatus;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;

public interface CmnStatusService {
	CmnStatusBean save(CmnStatusBean cmnStatus) throws RecordAlreadyExistsException;

	CmnStatusBean getById(String id) throws RecordNotFoundException;

	List<CmnStatusBean> getAll();

	void delete(String id) throws RecordNotFoundException;

	void delete(CmnStatus cmnStatus);
} 