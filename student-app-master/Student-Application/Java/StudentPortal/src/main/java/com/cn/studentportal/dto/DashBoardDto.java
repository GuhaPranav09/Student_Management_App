package com.cn.studentportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class DashBoardDto {
	
	private String stdStatusName;
	private Long stdStatusCount;
	

}
