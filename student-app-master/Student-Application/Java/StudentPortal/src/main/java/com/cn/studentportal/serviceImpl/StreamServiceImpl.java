package com.cn.studentportal.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.studentportal.bean.StreamBean;
import com.cn.studentportal.entity.Stream;
import com.cn.studentportal.exception.RecordAlreadyExistsException;
import com.cn.studentportal.exception.RecordNotFoundException;
import com.cn.studentportal.repository.StreamRepository;
import com.cn.studentportal.service.StreamService;
import com.cn.studentportal.util.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StreamServiceImpl implements StreamService {

	@Autowired
	private StreamRepository streamRepository;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public StreamBean save(StreamBean streamBean) throws RecordAlreadyExistsException {

		try {
			log.info("Saving the stream"); 
			Stream stream = new Stream();
			Stream findedStream = streamRepository.getByStreamName(streamBean.getStreamName());

			if (findedStream == null) {
				stream = beanToEntity(streamBean);
				stream.setCmnStatusId(CommonConstants.Active);
				;
				Stream dbStream = streamRepository.save(stream);

				StreamBean dbStreamBean = entityToBean(dbStream);

				return dbStreamBean;
			} else {
				throw new RecordAlreadyExistsException("Stream already exists");
			}
		} catch (RecordAlreadyExistsException exception) {
			log.error("Stream already exists", exception);
			throw new RecordAlreadyExistsException("Stream already exists");
		}

	}

	@Override
	public StreamBean update(StreamBean streamBean) throws RecordNotFoundException {

		try {
			log.info("updating the stream");
			Stream stream = new Stream();
			streamRepository.findById(streamBean.getStreamId())
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));

			stream = beanToEntity(streamBean);

			Stream dbStream = streamRepository.save(stream);

			StreamBean dbStreamBean = entityToBean(dbStream);

			return dbStreamBean;

		} catch (RecordNotFoundException exception) {
			log.error("Error occured while updating stream", exception);
			throw exception;
		}
	}

	@Override
	public StreamBean getById(int id) throws RecordNotFoundException {

		try {
			log.info("Retrieving stream by id");
			Stream stream = streamRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));

			StreamBean streamBean = entityToBean(stream);
			return streamBean;
		} catch (RecordNotFoundException exception) {
			log.error("Error occured while fetching stream by id", exception);
			throw exception;
		}
	}

	@Override
	public List<StreamBean> getAll() {

		try {
			log.info("Retrieving all streams");
			List<Stream> list = streamRepository.findAll();

			List<StreamBean> beanList = entityToBean(list);
			return beanList;
		} catch (Exception exception) {
			log.error("Error occured while fetching all streams", exception);
			throw exception;
		}

	}

	@Override
	public void delete(int id) throws RecordNotFoundException {
		try {
			log.info("Deleting stream by id");
			Stream stream = streamRepository.findById(id)
					.orElseThrow(() -> new RecordNotFoundException("No Record Found with given id"));
//			streamRepository.deleteById(id);
			stream.setCmnStatusId(CommonConstants.InActive);
			streamRepository.save(stream);


		} catch (RecordNotFoundException exception) {
			log.error("Error occured while deleting stream by id", exception);
			throw exception;
		}

	}

	public Stream beanToEntity(StreamBean streamBean) {

		Stream stream = objectMapper.convertValue(streamBean, Stream.class);
		return stream;
	}

	public StreamBean entityToBean(Stream stream) {
		StreamBean streamBean = objectMapper.convertValue(stream, StreamBean.class);
		return streamBean;
	}

	public List<StreamBean> entityToBean(List<Stream> list) {
		List<StreamBean> beanList = new ArrayList<>();
		for (Stream stream : list) {
			StreamBean streamBean = new StreamBean();
			streamBean = objectMapper.convertValue(stream, StreamBean.class);
			beanList.add(streamBean);
		}

		return beanList;
	}

	@Override
	public void delete(Stream stream) {
		if (stream.getCmnStatusId().equalsIgnoreCase(CommonConstants.Active)) {
			stream.setCmnStatusId(CommonConstants.InActive);
		} else {
			stream.setCmnStatusId(CommonConstants.Active);
		}
		streamRepository.save(stream);

	}

}
