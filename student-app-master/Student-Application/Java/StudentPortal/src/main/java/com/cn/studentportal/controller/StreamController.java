package com.cn.studentportal.controller;

import java.util.List;

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

import com.cn.studentportal.bean.StreamBean;
import com.cn.studentportal.entity.Stream;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.service.StreamService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/stream")
@Slf4j
@CrossOrigin("*")
public class StreamController {

	@Autowired
	private StreamService streamService;

	@PostMapping("/save")
	public ResponseEntity<StreamBean> save(@RequestBody StreamBean stream) {
		log.info("Saving Stream entity {}", stream.getStreamName());
		try {
			StreamBean streamBean = streamService.save(stream);

			ResponseEntity<StreamBean> responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
			log.info("Saving Stream entity is done");
			return responseEntity;
		} catch (Exception e) {
			log.error("Error occurred while saving stream", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<StreamBean> get(@PathVariable int id) {
		log.info("Retrieving Stream by Id {} ", id);
		try {
			StreamBean streamBean = streamService.getById(id);
			log.info("Retrived stream by id is successfully");
			return ResponseEntity.status(HttpStatus.OK).body(streamBean);
		} catch (Exception e) {
			log.error("Error occurred while retrieving stream", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/all")
	public ResponseEntity<List<StreamBean>> get() {
		log.info("Retrieving All Streams ");
		try {
			List<StreamBean> list = streamService.getAll();
			ResponseEntity<List<StreamBean>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
			log.info("Fetching All Stream details is done");
			return responseEntity;
		} catch (Exception e) {
			log.error("Error occurred while retrieving all streams", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		log.info("Deleting Stream by Id {} ", id);
		try {
			streamService.delete(id);
			ResponseEntity<String> responseEntity = new ResponseEntity<>(
					HttpStatus.OK);
			log.info("Deleted Stream by ID successfully");
			return responseEntity;
		} catch (RecordNotFoundException e) {
			log.error("Stream deleting failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error : " + e.getMessage() + "}");
		} catch (Exception e) {
			log.error("Error occurred while deleting stream", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Error occurred while updating stram\"}");
		}
	}

	@PutMapping
	public ResponseEntity<String> put(@RequestBody StreamBean streamBean) throws Exception {

		log.info("Updating Stream by id {} ", streamBean.getStreamId());
		try {
			streamService.update(streamBean);
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
	public void updateByStream(@RequestBody Stream stream) {
		log.info("Update the  stream status ");

		streamService.delete(stream);
		log.info("Updating stream status is done");
	}
}
