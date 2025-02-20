package com.drscan.web.primary.users.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Entity
@Table(name = "users", schema = "drscan_db", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email", "phone", "otp_key"}))
public class User {
    @Id
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
