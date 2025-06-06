package com.drscan.web.primary.permission.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @Column(name = "permission_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long permissionCode;

    @Column(name = "user_code")
    private long userCode;

    private String permission;

    public Permission(long userCode, String permission) {
        this.userCode = userCode;
        this.permission = permission;
    }

    public void update(PermissionRequestDto userDto) {
        this.permission = userDto.getPermission();
    }

}