package com.cn.studentportal.dto;

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
public class StudentDto {
	
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

	private String streamName;
	private String streamStatusId;
	private String specializationName;
	private String specializationStatusId;
	//private String referedStdName;
	private String stdStatusName;
    private Integer interestedInId;
    private Date dateOfJoin;
	

}
