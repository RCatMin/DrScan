package com.drscan.web.users.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
