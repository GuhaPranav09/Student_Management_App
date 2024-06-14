package com.cn.studentportal.bean;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class StudentBean {

	private Integer stdId;

	private String stdName;

	private String yop;

	private Integer streamId;

	private Integer specializationId;

	private String qualPercentage;

	private String contactNo;

	private String email;

	private String referedBy;

	private String address;

	private String stdStatusId;

	private BigDecimal amountPaid;
	private Integer interestedInId;
	private Date dateOfJoin;
	private String status;
	
}
