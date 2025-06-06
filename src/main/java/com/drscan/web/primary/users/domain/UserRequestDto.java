package com.drscan.web.primary.users.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private Integer userCode;
    private String accountType;
    private String username;
    private String password;
    private String hospital;
    private String department;
    private String name;
    private String email;
    private String phone;
    private String status;
    private String code;
    private Integer failCount;
}
