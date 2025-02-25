package com.drscan.web.primary.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClinicRequestDto {
    private Integer clinicCode;
    private String patientCode;
    private String userCode;
    private Timestamp clinicDate;
    private String context;
}
