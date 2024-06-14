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

import com.cn.studentportal.bean.CmnStatusBean;
import com.cn.studentportal.entity.CmnStatus;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.service.CmnStatusService;

@RestController
@RequestMapping("/cmnStatus")
@CrossOrigin("*")
public class CmnStatusController {

	@Autowired
	private CmnStatusService cmnStatusService;
	private static Logger log = LoggerFactory.getLogger(CmnStatusController.class.getSimpleName());

	@PostMapping("/save")
	public ResponseEntity<CmnStatusBean> save(@RequestBody CmnStatusBean cmnStatus) {
		log.info("Saving CmnStatus entity {}", cmnStatus.getCmnStatusName());
		try {
			

			ResponseEntity<CmnStatusBean> responseEntity = new ResponseEntity<>(cmnStatusService.save(cmnStatus),
					HttpStatus.CREATED);
			log.info("Saving CmnStatus entity is done");
			return responseEntity;
		} catch (Exception e) {
			log.error("Error occurred while saving cmnStatus", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<CmnStatusBean> get(@PathVariable String id) {
		log.info("Retrieving CmnStatus by Id {} ", id);
		try {
			CmnStatusBean cmnStatusBean = cmnStatusService.getById(id);
			log.info("Retrived cmnStatus by id is successfully");
			return ResponseEntity.status(HttpStatus.OK).body(cmnStatusBean);
		} catch (Exception e) {
			log.error("Error occurred while retrieving cmnStatus", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/all")
	public ResponseEntity<List<CmnStatusBean>> get() {
		log.info("Retrieving All CmnStatuss ");
		try {
			List<CmnStatusBean> list = cmnStatusService.getAll();
			ResponseEntity<List<CmnStatusBean>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
			log.info("Fetching All CmnStatus details is done");
			return responseEntity;
		} catch (Exception e) {
			log.error("Error occurred while retrieving all cmnStatuss", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		log.info("Deleting CmnStatus by Id {} ", id);
		try {
			cmnStatusService.delete(id);
			ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
			log.info("Deleted CmnStatus by ID successfully");
			return responseEntity;
		} catch (RecordNotFoundException e) {
			log.error("CmnStatus deleting failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			log.error("Error occurred while deleting cmnStatus", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating stram\"}");
		}
	}

	@PutMapping
	public ResponseEntity<String> put(@RequestBody CmnStatus cmnStatus) throws Exception {

		log.info("Updating CmnStatus {} ", cmnStatus.getCmnStatusId());
		try {
			CmnStatusBean cmnStatusBean = cmnStatusService.getById(cmnStatus.getCmnStatusId());
			CmnStatusBean updatedCmnStatusBean = null;
			if (cmnStatusBean != null) {
				cmnStatusBean.setCmnStatusName(cmnStatus.getCmnStatusName());
				updatedCmnStatusBean = cmnStatusService.save(cmnStatusBean);
			}
			ResponseEntity<String> responseEntity = new ResponseEntity<>(updatedCmnStatusBean.getCmnStatusId() + "",
					HttpStatus.OK);
			log.info("Updating cmnStatus is done");
			return responseEntity;
		} catch (RecordNotFoundException e) {
			log.error("CmnStatus updating failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			log.error("Error occurred while deleting cmnStatus", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating CmnStatus\"}");
		}
	}

	@PutMapping("/updatestatus")
	public void updateByCmnStatus(@RequestBody CmnStatus cmnStatus) {
		log.info("Update the  cmnStatus status ");

		cmnStatusService.delete(cmnStatus);
		log.info("Updating cmnStatus status is done");
	}
}
