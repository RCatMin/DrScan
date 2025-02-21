package com.drscan.web.primary.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequestDto {
    private long userCode;
    private String permission;
}