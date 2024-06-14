package com.cn.studentportal.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.studentportal.bean.SpecializationBean;
import com.cn.studentportal.bean.StreamBean;
import com.cn.studentportal.dto.SpecializationDto;
import com.cn.studentportal.dto.StudentDto;
import com.cn.studentportal.entity.Specialization;
import com.cn.studentportal.entity.Stream;
import com.cn.studentportal.entity.Student;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.repository.SpecializationRepository;
import com.cn.studentportal.service.SpecializationService;
import com.cn.studentportal.util.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpecializationServiceImpl implements SpecializationService {

	@Autowired
	private SpecializationRepository specializationRepository;
	@Autowired
	private ObjectMapper objectMapper;

//	private static Logger log = LoggerFactory.getLogger(SpecializationServiceImpl.class.getSimpleName());

	@Override
	public SpecializationBean save(SpecializationBean specializationBean) throws RecordAlreadyExistsException {

		try {
			log.info("Saving the specialization"); 
			Specialization specialization = new Specialization();
			Specialization findedSpecialization = specializationRepository.getBySpecializationNameAndStreamId(
					specializationBean.getSpecializationName(), specializationBean.getStreamId());

			if (findedSpecialization == null) {
				specialization = beanToEntity(specializationBean);
				specialization.setCmnStatusId(CommonConstants.Active);
				;
				Specialization dbSpecialization = specializationRepository.save(specialization);

				SpecializationBean dbSpecializationBean = entityToBean(dbSpecialization);

				return dbSpecializationBean;
			} else {
				throw new RecordAlreadyExistsException("Specialization already exists");
			}
		} catch (RecordAlreadyExistsException exception) {
			log.error("Error occured while saving specialization", exception);
			throw exception;
		}

	}

	@Override
	public SpecializationBean getById(int id) throws RecordNotFoundException {

		try {
			log.info("Retrieving specialization by id");
			Specialization specialization = specializationRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));

			SpecializationBean specializationBean = entityToBean(specialization);
			return specializationBean;
		} catch (RecordNotFoundException exception) {
			log.error("Error occured while fetching specialization by id", exception);
			throw exception;
		}
	}

	@Override
	public List<SpecializationBean> getAll() {

		try {
			log.info("Retrieving all specializations");
			List<Specialization> list = specializationRepository.findAll();

			List<SpecializationBean> beanList = entityToBean(list);
			return beanList;
		} catch (Exception exception) {
			log.error("Error occured while fetching all specializations", exception);
			throw exception;
		}

	}

	@Override
	public void delete(int id) throws RecordNotFoundException {
		try {
			log.info("Deleting specialization by id");
			Specialization specialization = specializationRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));
			specialization.setCmnStatusId(CommonConstants.InActive);

			Specialization specialization1 = specializationRepository.save(specialization);
		} catch (RecordNotFoundException exception) {
			log.error("Error occured while deleting specialization by id", exception);
			throw exception;
		}

	}

	public Specialization beanToEntity(SpecializationBean specializationBean) {

		Specialization specialization = objectMapper.convertValue(specializationBean, Specialization.class);
		return specialization;
	}

	public SpecializationBean entityToBean(Specialization specialization) {
		SpecializationBean specializationBean = objectMapper.convertValue(specialization, SpecializationBean.class);
		return specializationBean;
	}

	public List<SpecializationBean> entityToBean(List<Specialization> list) {
		List<SpecializationBean> beanList = new ArrayList<>();
		for (Specialization specialization : list) {
			SpecializationBean specializationBean = new SpecializationBean();
			specializationBean = objectMapper.convertValue(specialization, SpecializationBean.class);
			beanList.add(specializationBean);
		}

		return beanList;
	}

	@Override
	public void delete(Specialization specialization) {
		if (specialization.getCmnStatusId().equalsIgnoreCase(CommonConstants.Active)) {
			specialization.setCmnStatusId(CommonConstants.InActive);
		} else {
			specialization.setCmnStatusId(CommonConstants.Active);
		}
		specializationRepository.save(specialization);

	}

	@Override
	public Specialization update(SpecializationDto specializationDto) throws RecordNotFoundException {

		try {
			log.info("updating the stream");
			Specialization specialization = new Specialization();
			specializationRepository.findById(specializationDto.getSpecializationId())
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));

			specialization = splDtoToSplEntity(specializationDto);

			Specialization dbspecialization = specializationRepository.save(specialization);

			return dbspecialization;

		} catch (RecordNotFoundException exception) {
			log.error("Error occured while updating stream", exception);
			throw exception;
		}

	}

	@Override
	public List<SpecializationDto> getAllDtos() {
		try {
			log.info("Retrieving all Specializations");
			return specializationRepository.findAllDtos();
		} catch (Exception exception) {
			log.error("Error occured while fetching all Specializations", exception);
			throw exception;
		}

	}

	@Override
	public List<SpecializationDto> getDtosByStreamId(Integer streamId) {
		try {
			log.info("Retrieving all Specializations");
			return specializationRepository.findAllByStreamId(streamId);
		} catch (Exception exception) {
			log.error("Error occured while fetching all Specializations", exception);
			throw exception;
		}

	}

	public Specialization splDtoToSplEntity(SpecializationDto specializationDto) {
		Specialization specialization = Specialization.builder()
				.specializationId(specializationDto.getSpecializationId())
				.specializationName(specializationDto.getSpecializationName()).streamId(specializationDto.getStreamId())
				.cmnStatusId(specializationDto.getCmnStatusId()).build();

		return specialization;
	}
}
