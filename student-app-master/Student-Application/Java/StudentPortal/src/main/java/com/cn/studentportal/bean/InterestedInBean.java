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

 public class InterestedInBean {
    private Integer interestedInId;
    private String interestedInName;
    private String cmnStatusId;
    // Primary Key(s): interestedInId
// You can add getter/setter methods for primary key(s) if needed.
}