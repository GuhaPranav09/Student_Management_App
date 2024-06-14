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
@Table(name = "interested_in")
@DynamicInsert
@DynamicUpdate
public class InterestedIn {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interested_in_id")
	private Integer interestedInId;
	@Column(name = "interested_in_name")
	private String interestedInName;
	@Column(name = "cmn_status_id")
	private String cmnStatusId;

}