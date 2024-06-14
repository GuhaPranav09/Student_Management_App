package com.cn.studentportal.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.studentportal.bean.InterestedInBean;
import com.cn.studentportal.bean.SpecializationBean;
import com.cn.studentportal.entity.InterestedIn;
import com.cn.studentportal.entity.Specialization;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.repository.InterestedInRepository;
import com.cn.studentportal.service.InterestedInService;
import com.cn.studentportal.util.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InterestedInServiceImpl implements InterestedInService {

	@Autowired
	private InterestedInRepository interestedInRepository;
	@Autowired
	private ObjectMapper objectMapper;

//	private static Logger log = LoggerFactory.getLogger(InterestedInServiceImpl.class.getSimpleName());

	@Override
	public InterestedInBean save(InterestedInBean interestedInBean) throws RecordAlreadyExistsException {

		try {
			log.info("Saving the interestedIn"); 
			InterestedIn interestedIn = new InterestedIn();
			InterestedIn findedInterestedIn = interestedInRepository
					.getByInterestedInName(interestedInBean.getInterestedInName());

			if (findedInterestedIn == null) {
				interestedIn = beanToEntity(interestedInBean);
				interestedIn.setCmnStatusId(CommonConstants.Active);
				;
				InterestedIn dbInterestedIn = interestedInRepository.save(interestedIn);

				InterestedInBean dbInterestedInBean = entityToBean(dbInterestedIn);

				return dbInterestedInBean;
			} else {
				throw new RecordAlreadyExistsException("InterestedIn already exists");
			}
		} catch (RecordAlreadyExistsException exception) {
			log.error("Error occured while saving interestedIn", exception);
			throw exception;
		}

	}

	@Override
	public InterestedInBean getById(int id) throws RecordNotFoundException {

		try {
			log.info("Retrieving interestedIn by id");
			InterestedIn interestedIn = interestedInRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));

			InterestedInBean interestedInBean = entityToBean(interestedIn);
			return interestedInBean;
		} catch (RecordNotFoundException exception) {
			log.error("Error occured while fetching interestedIn by id", exception);
			throw exception;
		}
	}

	@Override
	public List<InterestedInBean> getAll() {

		try {
			log.info("Retrieving all interestedIns");
			List<InterestedIn> list = interestedInRepository.findAll();

			List<InterestedInBean> beanList = entityToBean(list);
			return beanList;
		} catch (Exception exception) {
			log.error("Error occured while fetching all interestedIns", exception);
			throw exception;
		}

	}

	@Override
	public void delete(int id) throws RecordNotFoundException {
		try {
			log.info("Deleting interestedIn by id");

			InterestedIn interestedIn = interestedInRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));
			interestedIn.setCmnStatusId(CommonConstants.InActive);

			InterestedIn interestedIn2 = interestedInRepository.save(interestedIn);

		} catch (RecordNotFoundException exception) {
			log.error("Error occured while deleting interestedIn by id", exception);
			throw exception;
		}

	}

	public InterestedIn beanToEntity(InterestedInBean interestedInBean) {

		InterestedIn interestedIn = objectMapper.convertValue(interestedInBean, InterestedIn.class);
		return interestedIn;
	}

	public InterestedInBean entityToBean(InterestedIn interestedIn) {
		InterestedInBean interestedInBean = objectMapper.convertValue(interestedIn, InterestedInBean.class);
		return interestedInBean;
	}

	public List<InterestedInBean> entityToBean(List<InterestedIn> list) {
		List<InterestedInBean> beanList = new ArrayList<>();
		for (InterestedIn interestedIn : list) {
			InterestedInBean interestedInBean = new InterestedInBean();
			interestedInBean = objectMapper.convertValue(interestedIn, InterestedInBean.class);
			beanList.add(interestedInBean);
		}

		return beanList;
	}

	@Override
	public void delete(InterestedIn interestedIn) {
		if (interestedIn.getCmnStatusId().equalsIgnoreCase(CommonConstants.Active)) {
			interestedIn.setCmnStatusId(CommonConstants.InActive);
		} else {
			interestedIn.setCmnStatusId(CommonConstants.Active);
		}
		interestedInRepository.save(interestedIn);

	}

	@Override
	public InterestedInBean update(InterestedInBean interestedInBean) throws RecordNotFoundException {
		try {
			InterestedIn interestedIn = new InterestedIn();
			interestedInRepository.findById(interestedInBean.getInterestedInId())
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));
			interestedIn = beanToEntity(interestedInBean);
			InterestedIn interestedIn2 = interestedInRepository.save(interestedIn);
			InterestedInBean dbInterestedInBean = entityToBean(interestedIn2);
			return dbInterestedInBean;

		} catch (RecordNotFoundException exception) {
			log.error("Error occured while updating InterestedIn", exception);
			throw exception;
		}

	}

}
