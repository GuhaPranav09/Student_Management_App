package com.cn.studentportal.serviceImpl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.studentportal.bean.CmnStatusBean;
import com.cn.studentportal.entity.CmnStatus;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.repository.CmnStatusRepository;
import com.cn.studentportal.service.CmnStatusService;
import com.cn.studentportal.util.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CmnStatusServiceImpl implements CmnStatusService {

	@Autowired
	private CmnStatusRepository cmnStatusRepository;
	@Autowired
	private ObjectMapper objectMapper ;

//	private static Logger log = LoggerFactory.getLogger(CmnStatusServiceImpl.class.getSimpleName());

	@Override
	public CmnStatusBean save(CmnStatusBean cmnStatusBean) throws  RecordAlreadyExistsException{

	
		try {
			log.info("Saving the cmnStatus");
			CmnStatus cmnStatus = new CmnStatus();
			CmnStatus findedCmnStatus= cmnStatusRepository.getByCmnStatusName(cmnStatusBean.getCmnStatusName());
					
			if (findedCmnStatus == null) {
				 cmnStatus = beanToEntity(cmnStatusBean);
				
				 CmnStatus dbCmnStatus = cmnStatusRepository.save(cmnStatus);
				
				 CmnStatusBean dbCmnStatusBean = entityToBean(dbCmnStatus);
				
				return dbCmnStatusBean;
			} else {
				throw new RecordAlreadyExistsException("CmnStatus already exists");
			}
		} catch (RecordAlreadyExistsException exception) {
			log.error("Error occured while saving cmnStatus", exception);
			throw exception;
		}

	}

	@Override
	public CmnStatusBean getById(String id) throws RecordNotFoundException {
	
		try {
			log.info("Retrieving cmnStatus by id");
			CmnStatus cmnStatus = cmnStatusRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));
			
			CmnStatusBean cmnStatusBean  = entityToBean(cmnStatus);
			return cmnStatusBean;
		} catch (RecordNotFoundException exception) {
			log.error("Error occured while fetching cmnStatus by id", exception);
			throw exception;
		}
	}

	@Override
	public List<CmnStatusBean> getAll() {
		
		try {
			log.info("Retrieving all cmnStatuss");
			List<CmnStatus> list = cmnStatusRepository.findAll();
			 
			 List<CmnStatusBean> beanList = entityToBean(list );
			return beanList;
		} catch (Exception exception) {
			log.error("Error occured while fetching all cmnStatuss", exception);
			throw exception;
		}

	}

	@Override
	public void delete(String id) throws RecordNotFoundException {
		try {
			log.info("Deleting cmnStatus by id");
			 cmnStatusRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));
			cmnStatusRepository.deleteById(id);
			
		} catch (RecordNotFoundException exception) {
			log.error("Error occured while deleting cmnStatus by id", exception);
			throw exception;
		}

	}

	public CmnStatus beanToEntity(CmnStatusBean cmnStatusBean) {
		
		CmnStatus cmnStatus = objectMapper.convertValue(cmnStatusBean, CmnStatus.class);
		return cmnStatus;
	}

	public CmnStatusBean entityToBean(CmnStatus cmnStatus) {
		 CmnStatusBean cmnStatusBean = objectMapper.convertValue(cmnStatus, CmnStatusBean.class);
		return cmnStatusBean;
	}
	
	

	public List<CmnStatusBean> entityToBean(List<CmnStatus> list) {
		List<CmnStatusBean> beanList = new ArrayList<>();
		for (CmnStatus cmnStatus : list) {
			CmnStatusBean cmnStatusBean = new CmnStatusBean();
			cmnStatusBean = objectMapper.convertValue(cmnStatus, CmnStatusBean.class);
			beanList.add(cmnStatusBean);
		}

		return beanList;
	}

	@Override
	public void delete(CmnStatus cmnStatus) {
		if (cmnStatus.getCmnStatusId().equalsIgnoreCase(CommonConstants.Active)) {
			cmnStatus.setCmnStatusId(CommonConstants.InActive);
		} else {
			cmnStatus.setCmnStatusId(CommonConstants.Active);
		}
		cmnStatusRepository.save(cmnStatus);

	}
}
