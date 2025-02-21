package com.drscan.web.primary.users.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Entity
@Table(name = "users", schema = "drscan_db", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email", "phone", "otp_key"}))
public class User {
    @Id
    @Column(name = "user_code")
    private Integer userCode;

    @Column(name = "account_type")
    private String accountType;

    private String username;
    private String password;

    @Column(name = "hospital_name")
    private String hospitalName;

    private String department;
    private String name;
    private String email;
    private String phone;

    @Column(name = "otp_key")
    private String otpKey;

    private String status;

    @Column(name = "fail_count")
    private Integer failCount;

    @Column(name = "reg_date")
    private Timestamp regDate;

    @Column(name = "mod_date")
    private Timestamp modDate;
}
