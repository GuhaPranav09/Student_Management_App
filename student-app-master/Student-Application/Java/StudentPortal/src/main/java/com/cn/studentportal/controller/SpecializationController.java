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

import com.cn.studentportal.bean.SpecializationBean;
import com.cn.studentportal.dto.SpecializationDto;
import com.cn.studentportal.entity.Specialization;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.service.SpecializationService;
import com.cn.studentportal.util.CommonConstants;

@RestController
@RequestMapping("/specialization")
@CrossOrigin("*")
public class SpecializationController {

	@Autowired
	private SpecializationService specializationService;
	private static Logger log = LoggerFactory.getLogger(SpecializationController.class.getSimpleName());

	@PostMapping("/save")
	public ResponseEntity<SpecializationBean> save(@RequestBody SpecializationBean specialization) {
		log.info("Saving Specialization entity {}", specialization.getSpecializationName());
		try {
			specialization.setCmnStatusId(CommonConstants.Active);

			ResponseEntity<SpecializationBean> responseEntity = new ResponseEntity<>(specializationService.save(specialization),
					HttpStatus.CREATED);
			log.info("Saving Specialization entity is done");
			return responseEntity;
		} catch (Exception e) {
			log.error("Error occurred while saving specialization", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<SpecializationBean> get(@PathVariable int id) {
		log.info("Retrieving Specialization by Id {} ", id);
		try {
			SpecializationBean specializationBean = specializationService.getById(id);
			log.info("Retrived specialization by id is successfully");
			return ResponseEntity.status(HttpStatus.OK).body(specializationBean);
		} catch (Exception e) {
			log.error("Error occurred while retrieving specialization", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/all")
	public ResponseEntity<List<SpecializationBean>> get() {
		log.info("Retrieving All Specializations ");
		try {
			List<SpecializationBean> list = specializationService.getAll();
			ResponseEntity<List<SpecializationBean>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
			log.info("Fetching All Specialization details is done");
			return responseEntity;
		} catch (Exception e) {
			log.error("Error occurred while retrieving all specializations", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		log.info("Deleting Specialization by Id {} ", id);
		try {
			specializationService.delete(id);
			ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
			log.info("Deleted Specialization by ID successfully");
			return responseEntity;
		} catch (RecordNotFoundException e) {
			log.error("Specialization deleting failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			log.error("Error occurred while deleting specialization", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating stram\"}");
		}
	}
	@GetMapping("/alldtos")
	public ResponseEntity<List<SpecializationDto>> getDtos() {
		log.info("Retrieving All Specilization ");
		try {
			List<SpecializationDto> list = specializationService.getAllDtos();
			log.info("Fetching All Specilization details is done");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			log.error("Error occurred while retrieving all Specilization", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@GetMapping("/alldtos/{streamId}")
	public ResponseEntity<List<SpecializationDto>> getDtosByStreamId(@PathVariable Integer streamId) {
		log.info("Retrieving All Specilization getDtosByStreamId(): {} ", streamId);
		try {
			List<SpecializationDto> list = specializationService.getDtosByStreamId(streamId);
			log.info("Fetching All Specilization details is done");
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			log.error("Error occurred while retrieving all Specilization", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	
	@PutMapping
	public ResponseEntity<String> put(@RequestBody SpecializationDto specializationDto) throws Exception {

		log.info("Updating Specialization {} ", specializationDto.getSpecializationId());
		try {
			specializationService.update(specializationDto);
			ResponseEntity<String> responseEntity = new ResponseEntity<>(
					HttpStatus.OK);
			log.info("Updating specialization is done");
			return responseEntity;
		} catch (RecordNotFoundException e) {
			log.error("Specialization updating failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"" + e.getMessage() + "\"}");
		} catch (Exception e) {
			log.error("Error occurred while deleting specialization", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating Specialization\"}");
		}
	}

	@PutMapping("/updatestatus")
	public void updateBySpecialization(@RequestBody Specialization specialization) {
		log.info("Update the  specialization status ");

		specializationService.delete(specialization);
		log.info("Updating specialization status is done");
	}
}
