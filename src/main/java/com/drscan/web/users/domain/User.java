package com.drscan.web.users.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.security.Timestamp;

@Getter
@Entity
@Table(name = "users", schema = "drscan_db", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email", "phone", "otp_key"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userCode;

    private String accountType;
    private String username;
    private String password;
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
