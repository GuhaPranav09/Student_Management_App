package com.cn.studentportal.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "std_status")
@DynamicInsert
@DynamicUpdate
public class StdStatus {
	@Id
	@Column(name = "std_status_id")
	private String stdStatusId;
	@Column(name = "std_status_name")
	private String stdStatusName;
	@Column(name="cmn_status")
	private String cmnStatus;
	// Primary Key(s): stdStatusId
// You can add getter/setter methods for primary key(s) if needed.
}