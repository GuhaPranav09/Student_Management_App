package com.cn.studentportal.bean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

 public class SpecializationBean {
    private Integer specializationId;
    private String specializationName;
    private Integer streamId;
    private String cmnStatusId;
    // Primary Key(s): specializationId
// You can add getter/setter methods for primary key(s) if needed.
}