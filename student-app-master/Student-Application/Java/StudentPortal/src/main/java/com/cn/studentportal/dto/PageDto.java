package com.cn.studentportal.dto;

import java.util.List;

import com.cn.studentportal.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PageDto<T, N> {

	private int pageNo;
	private int pageSize;
	private boolean last;
	private boolean first;
	private int totalPages;
	private Long totalRecords;
	private T records;
	private N count;
}
