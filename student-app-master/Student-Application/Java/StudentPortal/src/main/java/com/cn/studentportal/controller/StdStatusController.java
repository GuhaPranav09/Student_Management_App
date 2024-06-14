package com.cn.studentportal.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.studentportal.bean.StdStatusBean;
import com.cn.studentportal.bean.StreamBean;
import com.cn.studentportal.entity.StdStatus;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.service.StdStatusService;
import com.cn.studentportal.util.CommonConstants;

@RestController
@RequestMapping("/stdStatus")
@CrossOrigin("*")
public class StdStatusController {

	@Autowired
	private StdStatusService stdStatusService;
	private static Logger log = LoggerFactory.getLogger(StdStatusController.class.getSimpleName());

	@PostMapping("/save")
	public ResponseEntity<StdStatusBean> save(@RequestBody StdStatusBean stdStatus) {
		log.info("Saving StdStatus entity {}", stdStatus.getStdStatusName());
		try {
			
			stdStatus.setCmnStatus(CommonConstants.Active);
			ResponseEntity<StdStatusBean> responseEntity = new ResponseEntity<>(stdStatusService.save(stdStatus),
					HttpStatus.CREATED);
			log.info("Saving StdStatus entity is done");
			return responseEntity;
		} catch (Exception e) {
			log.error("Error occurred while saving stdStatus", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<StdStatusBean> get(@PathVariable String id) {
		log.info("Retrieving StdStatus by Id {} ", id);
		try {
			StdStatusBean stdStatusBean = stdStatusService.getById(id);
			log.info("Retrived stdStatus by id is successfully");
			return ResponseEntity.status(HttpStatus.OK).body(stdStatusBean);
		} catch (Exception e) {
			log.error("Error occurred while retrieving stdStatus", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/all")
	public ResponseEntity<List<StdStatusBean>> get() {
		log.info("Retrieving All StdStatuss ");
		try {
			List<StdStatusBean> list = stdStatusService.getAll();
			ResponseEntity<List<StdStatusBean>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
			log.info("Fetching All StdStatus details is done");
			return responseEntity;
		} catch (Exception e) {
			log.error("Error occurred while retrieving all stdStatuss", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		log.info("Deleting StdStatus by Id {} ", id);
		try {
			stdStatusService.delete(id);
			ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
			log.info("Deleted StdStatus by ID successfully");
			return responseEntity;
		} catch (RecordNotFoundException e) {
			log.error("StdStatus deleting failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			log.error("Error occurred while deleting stdStatus", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating stram\"}");
		}
	}

	@PutMapping
	public ResponseEntity<String> put(@RequestBody StdStatusBean stdStatusBean) throws Exception {

		log.info("Updating Stream by id {} ", stdStatusBean.getStdStatusId());
		try {
			stdStatusService.update(stdStatusBean);
			ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
			log.info("Updating stream is done");
			return responseEntity;
		} catch (RecordNotFoundException e) {
			log.error("Stream updating failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error : " + e.getMessage() + "}");
		} catch (Exception e) {
			log.error("Error occurred while deleting stream", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating Stream\"}");
		}
	}

	@PutMapping("/updatestatus")
	public void updateByStdStatus(@RequestBody StdStatus stdStatus) {
		log.info("Update the  stdStatus status ");

		stdStatusService.delete(stdStatus);
		log.info("Updating stdStatus status is done");
	}
}
