package com.cn.studentportal.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailBean {
	private String toAddress;
	private String ccAddress;
	private String emailsubject;
	private String emailBody;

}
