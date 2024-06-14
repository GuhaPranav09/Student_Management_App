package com.cn.studentportal.service;


import java.util.List;

import com.cn.studentportal.bean.SpecializationBean;
import com.cn.studentportal.dto.SpecializationDto;
import com.cn.studentportal.entity.Specialization;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;

public interface SpecializationService {
	SpecializationBean save(SpecializationBean specialization) throws RecordAlreadyExistsException;

	SpecializationBean getById(int id) throws RecordNotFoundException;

	List<SpecializationBean> getAll();

	void delete(int id) throws RecordNotFoundException;

	void delete(Specialization specialization);

	Specialization update(SpecializationDto specializationDto) throws RecordNotFoundException ;

	List<SpecializationDto> getAllDtos();

	List<SpecializationDto> getDtosByStreamId(Integer streamId);
}
