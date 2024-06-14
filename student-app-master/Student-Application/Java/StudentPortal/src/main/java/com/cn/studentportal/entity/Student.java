package com.cn.studentportal.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "std_id")
	private Integer stdId;

	@Column(name = "std_name")
	private String stdName;

	@Column(name = "year_of_pass")
	private String yop;

	@Column(name = "stream_id")
	private Integer streamId;

	@Column(name = "specialization_id")
	private Integer specializationId;

	@Column(name = "qual_percentage")
	private String qualPercentage;

	@Column(name = "contact_no")
	private String contactNo;

	@Column(name = "email")
	private String email;

	@Column(name = "refered_by")
	private String referedBy;

	@Column(name = "address")
	private String address;

	@Column(name = "std_status_id")
	private String stdStatusId;
	@Column(name = "amount_paid")
	private BigDecimal amountPaid;
	@Column(name = "interested_in_id")
	private Integer interestedInId;
	@Column(name = "date_of_join")
	private Date dateOfJoin;
	@Column(name = "status")
	private String status;
	


	// # std_id, std_name, year_of_pass, stream_id, specialization_id,
	// qual_percentage, contact_no, email, refered_by, address, std_status_id

}
