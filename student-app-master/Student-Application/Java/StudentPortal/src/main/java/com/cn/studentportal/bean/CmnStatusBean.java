package com.cn.studentportal.bean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

 public class CmnStatusBean {
    private String cmnStatusId;
    private String cmnStatusName;
    // Primary Key(s): cmnStatusId
// You can add getter/setter methods for primary key(s) if needed.
}