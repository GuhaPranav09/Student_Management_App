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
@Table(name = "cmn_status")
@DynamicInsert
@DynamicUpdate
public class CmnStatus {
	@Id
	@Column(name = "cmn_status_id")
	private String cmnStatusId;
	@Column(name = "cmn_status_name")
	private String cmnStatusName;
	// Primary Key(s): cmnStatusId
// You can add getter/setter methods for primary key(s) if needed.
}