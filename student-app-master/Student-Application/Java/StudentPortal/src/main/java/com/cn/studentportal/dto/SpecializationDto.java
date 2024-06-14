package com.cn.studentportal.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class SpecializationDto {
    private Integer specializationId;
    private String specializationName;
    private Integer streamId;
    private String cmnStatusId;
    
    private String streamName;
    

}
