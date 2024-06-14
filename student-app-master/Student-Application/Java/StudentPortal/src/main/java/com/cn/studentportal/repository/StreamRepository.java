package com.cn.studentportal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cn.studentportal.entity.Stream;

@Repository
public interface StreamRepository extends JpaRepository<Stream, Integer> {
	Stream getByStreamName(String name);
}
