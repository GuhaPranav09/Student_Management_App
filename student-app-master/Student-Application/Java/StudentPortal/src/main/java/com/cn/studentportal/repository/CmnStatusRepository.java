package com.cn.studentportal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cn.studentportal.entity.CmnStatus;

@Repository
public interface CmnStatusRepository extends JpaRepository<CmnStatus, String> {
	CmnStatus getByCmnStatusName(String name);
}
