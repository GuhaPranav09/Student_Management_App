package com.cn.studentportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cn.studentportal.entity.Otp;

import jakarta.transaction.Transactional;

public interface OtpRepository extends JpaRepository<Otp, Integer>{

	Optional<Otp> findByEmail(String email);

	@Modifying
	@Transactional
	@Query("DELETE FROM Otp o WHERE TIMESTAMPDIFF(MINUTE, o.expirationTime, CURRENT_TIMESTAMP()) <> 2 AND o.id > 0")
	void deleteExpiredOtps();
}
