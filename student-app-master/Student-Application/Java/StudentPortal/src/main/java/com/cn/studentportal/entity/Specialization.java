package com.cn.studentportal.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "specialization")
@DynamicInsert
@DynamicUpdate
@Builder
public class Specialization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "specialization_id")
	private Integer specializationId;
	@Column(name = "specialization_name")
	private String specializationName;
	@Column(name = "stream_id")
	private Integer streamId;
	@Column(name = "cmn_status_id")
	private String cmnStatusId;
	// Primary Key(s): specializationId
// You can add getter/setter methods for primary key(s) if needed.
}