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

import com.cn.studentportal.bean.InterestedInBean;
import com.cn.studentportal.bean.SpecializationBean;
import com.cn.studentportal.entity.InterestedIn;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.service.InterestedInService;
import com.cn.studentportal.util.CommonConstants;

@RestController
@RequestMapping("/interestedIn")
@CrossOrigin("*")
public class InterestedInController {

	@Autowired
	private InterestedInService interestedInService;
	private static Logger log = LoggerFactory.getLogger(InterestedInController.class.getSimpleName());

	@PostMapping("/save")
	public ResponseEntity<InterestedInBean> save(@RequestBody InterestedInBean interestedIn) {
		log.info("Saving InterestedIn entity {}", interestedIn.getInterestedInName());
		try {
			interestedIn.setCmnStatusId(CommonConstants.Active);

			ResponseEntity<InterestedInBean> responseEntity = new ResponseEntity<>(
					interestedInService.save(interestedIn), HttpStatus.CREATED);
			log.info("Saving InterestedIn entity is done");
			return responseEntity;
		} catch (Exception e) {
			log.error("Error occurred while saving interestedIn", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<InterestedInBean> get(@PathVariable int id) {
		log.info("Retrieving InterestedIn by Id {} ", id);
		try {
			InterestedInBean interestedInBean = interestedInService.getById(id);
			log.info("Retrived interestedIn by id is successfully");
			return ResponseEntity.status(HttpStatus.OK).body(interestedInBean);
		} catch (Exception e) {
			log.error("Error occurred while retrieving interestedIn", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/all")
	public ResponseEntity<List<InterestedInBean>> get() {
		log.info("Retrieving All InterestedIns ");
		try {
			List<InterestedInBean> list = interestedInService.getAll();
			ResponseEntity<List<InterestedInBean>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
			log.info("Fetching All InterestedIn details is done");
			return responseEntity;
		} catch (Exception e) {
			log.error("Error occurred while retrieving all interestedIns", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		log.info("Deleting InterestedIn by Id {} ", id);
		try {
			interestedInService.delete(id);
			ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
			log.info("Deleted InterestedIn by ID successfully");
			return responseEntity;
		} catch (RecordNotFoundException e) {
			log.error("InterestedIn deleting failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			log.error("Error occurred while deleting interestedIn", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating stram\"}");
		}
	}

	@PutMapping
	public ResponseEntity<String> put(@RequestBody InterestedInBean interestedIn) throws Exception {

		log.info("Updating InterestedIn {} ", interestedIn.getInterestedInId());
		try {
			interestedInService.update(interestedIn);
			ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
			log.info("Updating interestedIn is done");
			return responseEntity;
		} catch (RecordNotFoundException e) {
			log.error("InterestedIn updating failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			log.error("Error occurred while deleting interestedIn", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating InterestedIn\"}");
		}
	}

	@PutMapping("/updatestatus")
	public void updateByInterestedIn(@RequestBody InterestedIn interestedIn) {
		log.info("Update the  interestedIn status ");

		interestedInService.delete(interestedIn);
		log.info("Updating interestedIn status is done");
	}
}
