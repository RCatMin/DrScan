package com.drscan.web.primary.users.domain;

import com.drscan.web.primary.users.util.Timestamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "users", schema = "drscan_db", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email", "phone", "otp_key"}))
public class User extends Timestamp {
    @Id
    @Column(name = "user_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public User(String username, String password, String hospital, String department, String name, String email, String phone, String otpKey) {
        this.username = username;
        this.password = password;
        this.hospitalName = hospital;
        this.department = department;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.otpKey = otpKey;
        this.accountType = "temporary";
        this.status = "pending";
        this.failCount = 0;
    }

    public void update(UserRequestDto userDto) {
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.status = userDto.getStatus();
        this.accountType = userDto.getAccountType();
    }

    public void update2(UserRequestDto userDto) {
        this.password = userDto.getPassword();
        this.hospitalName = userDto.getHospital();
        this.department = userDto.getDepartment();
        this.name = userDto.getName();
        this.phone = userDto.getPhone();
    }

    public void update3() {
        this.status = "pending";
    }

    public void approve(String accountType, String status) {
        this.status = status;
        this.accountType = accountType;
    }

    public void incrementFailCountAndSuspended() {
        this.failCount++;

        if (this.failCount >= 5) {
            this.status = "suspended";
        }
    }

    public void resetFailCount() {
        this.failCount = 0;
    }
}