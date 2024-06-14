package com.cn.studentportal.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.studentportal.bean.SpecializationBean;
import com.cn.studentportal.bean.StdStatusBean;
import com.cn.studentportal.entity.Specialization;
import com.cn.studentportal.entity.StdStatus;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.repository.StdStatusRepository;
import com.cn.studentportal.service.StdStatusService;
import com.cn.studentportal.util.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StdStatusServiceImpl implements StdStatusService {

	@Autowired
	private StdStatusRepository stdStatusRepository;
	@Autowired
	private ObjectMapper objectMapper;

//	private static Logger log = LoggerFactory.getLogger(StdStatusServiceImpl.class.getSimpleName());

	@Override
	public StdStatusBean save(StdStatusBean stdStatusBean) throws RecordAlreadyExistsException {

		try {
			log.info("Saving the stdStatus"); 
			StdStatus stdStatus = new StdStatus();
			StdStatus findedStdStatus = stdStatusRepository.getByStdStatusName(stdStatusBean.getStdStatusName());

			if (findedStdStatus == null) {
				stdStatus = beanToEntity(stdStatusBean);

				StdStatus dbStdStatus = stdStatusRepository.save(stdStatus);

				StdStatusBean dbStdStatusBean = entityToBean(dbStdStatus);

				return dbStdStatusBean;
			} else {
				throw new RecordAlreadyExistsException("StdStatus already exists");
			}
		} catch (RecordAlreadyExistsException exception) {
			log.error("Error occured while saving stdStatus", exception);
			throw exception;
		}

	}

	@Override
	public StdStatusBean getById(String id) throws RecordNotFoundException {

		try {
			log.info("Retrieving stdStatus by id");
			StdStatus stdStatus = stdStatusRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));

			StdStatusBean stdStatusBean = entityToBean(stdStatus);
			return stdStatusBean;
		} catch (RecordNotFoundException exception) {
			log.error("Error occured while fetching stdStatus by id", exception);
			throw exception;
		}
	}

	@Override
	public List<StdStatusBean> getAll() {

		try {
			log.info("Retrieving all stdStatuss");
			List<StdStatus> list = stdStatusRepository.findAll();

			List<StdStatusBean> beanList = entityToBean(list);
			return beanList;
		} catch (Exception exception) {
			log.error("Error occured while fetching all stdStatuss", exception);
			throw exception;
		}

	}

	@Override
	public void delete(String id) throws RecordNotFoundException {
		try {
			log.info("Deleting stdStatus by id");
			StdStatus status = stdStatusRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));
//			stdStatusRepository.deleteById(id);
			status.setCmnStatus(CommonConstants.InActive);
			StdStatus status2 = stdStatusRepository.save(status);

		} catch (RecordNotFoundException exception) {
			log.error("Error occured while deleting stdStatus by id", exception);
			throw exception;
		}

	}

	public StdStatus beanToEntity(StdStatusBean stdStatusBean) {

		StdStatus stdStatus = objectMapper.convertValue(stdStatusBean, StdStatus.class);
		return stdStatus;
	}

	public StdStatusBean entityToBean(StdStatus stdStatus) {
		StdStatusBean stdStatusBean = objectMapper.convertValue(stdStatus, StdStatusBean.class);
		return stdStatusBean;
	}

	public List<StdStatusBean> entityToBean(List<StdStatus> list) {
		List<StdStatusBean> beanList = new ArrayList<>();
		for (StdStatus stdStatus : list) {
			StdStatusBean stdStatusBean = new StdStatusBean();
			stdStatusBean = objectMapper.convertValue(stdStatus, StdStatusBean.class);
			beanList.add(stdStatusBean);
		}

		return beanList;
	}

	@Override
	public void delete(StdStatus stdStatus) {
		if (stdStatus.getStdStatusId().equalsIgnoreCase(CommonConstants.Active)) {
			stdStatus.setStdStatusId(CommonConstants.InActive);
		} else {
			stdStatus.setStdStatusId(CommonConstants.Active);
		}
		stdStatusRepository.save(stdStatus);

	}

	@Override
	public StdStatusBean update(StdStatusBean stdStatusBean) throws RecordNotFoundException {
		
		try {
			log.info("updating the stream");
			StdStatus  stdStatus = new StdStatus();
			stdStatusRepository.findById(stdStatusBean.getStdStatusId())
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));

			stdStatus= beanToEntity(stdStatusBean);

			StdStatus dbStdStatus = stdStatusRepository.save(stdStatus);

			StdStatusBean stdStatusBean1 = entityToBean(dbStdStatus);

			return stdStatusBean1;

		} catch (RecordNotFoundException exception) {
			log.error("Error occured while updating stream", exception);
			throw exception;
		}
		
	}
}
