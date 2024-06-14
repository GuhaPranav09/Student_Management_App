package com.cn.studentportal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cn.studentportal.entity.InterestedIn;

@Repository
public interface InterestedInRepository extends JpaRepository<InterestedIn, Integer> {
	InterestedIn getByInterestedInName(String name);
}
