package com.cn.studentportal.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cn.studentportal.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User getByName(String name);

	Optional<User> findByUserEmailAndUserPassword(String email, String password);

	Optional<User> findByUserEmail(String userName);
	
	@Query(value = "SELECT * FROM user u WHERE u.name LIKE %?1% order by u.cmn_status_id,u.user_id desc ", nativeQuery = true)
	Page<User> findByUserByName(String userName, Pageable pageable);
	
	@Query(value="SELECT COUNT(*) FROM user", nativeQuery = true)
	int getStudentsCount();

//	Page<User> getStudentsByReferedName(String name, Pageable pageable);

	
}
