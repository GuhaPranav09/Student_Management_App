package com.cn.studentportal.bean;
import com.cn.studentportal.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
 public class UserBean {
    private Integer userId;
    private String name;
    private String userEmail;
    private String userContact;
    private String cmnStatusId;
	private String userPassword;
	private String isAdmin;
	private Role role;
    
    // Primary Key(s): userId
// You can add getter/setter methods for primary key(s) if needed.
}