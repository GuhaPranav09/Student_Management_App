package com.cn.studentportal.bean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

 public class StreamBean {
    private Integer streamId;
    private String streamName;
    private String cmnStatusId;
    // Primary Key(s): streamId
// You can add getter/setter methods for primary key(s) if needed.
}