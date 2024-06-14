package com.cn.studentportal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cn.studentportal.entity.StdStatus;

@Repository
public interface StdStatusRepository extends JpaRepository<StdStatus, String> {
	StdStatus getByStdStatusName(String name);
}
