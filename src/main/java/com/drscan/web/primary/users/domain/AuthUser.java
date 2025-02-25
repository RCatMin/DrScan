package com.drscan.web.primary.users.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Timestamp;

@Getter
@AllArgsConstructor
public class AuthUser {

    private Integer userCode;
    private String accountType;
    private String username;
    private String hospitalName;
    private String department;
    private String name;
    private String email;
    private String phone;
    private String otpKey;
    private String status;
    private Integer failCount;
    private Timestamp regDate;
    private Timestamp modDate;
}
