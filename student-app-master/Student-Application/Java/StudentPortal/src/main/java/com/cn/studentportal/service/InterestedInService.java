package com.cn.studentportal.service;


import java.util.List;

import com.cn.studentportal.bean.InterestedInBean;
import com.cn.studentportal.entity.InterestedIn;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;

public interface InterestedInService {
	InterestedInBean save(InterestedInBean interestedIn) throws RecordAlreadyExistsException;

	InterestedInBean getById(int id) throws RecordNotFoundException;

	List<InterestedInBean> getAll();

	void delete(int id) throws RecordNotFoundException;

	void delete(InterestedIn interestedIn);

	InterestedInBean update(InterestedInBean interestedIn) throws RecordNotFoundException ;
}
