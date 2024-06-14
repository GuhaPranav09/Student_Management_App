package com.cn.studentportal.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class StdStatusBean {
	private String stdStatusId;
	private String stdStatusName;
	private String cmnStatus;
	// Primary Key(s): stdStatusId
// You can add getter/setter methods for primary key(s) if needed.
}