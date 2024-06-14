package com.cn.studentportal.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterRequest {
    private String userName;
    private String userEmail;
    private String userContact;
    private String cmnStatusId;
    private String userPassword;
    private String isAdmin;
    private String role;
}
