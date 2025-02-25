package com.drscan.web.primary.clinic.domain;

import com.drscan.web.primary.users.util.ResponseDto;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public class ClinicResponseDto extends ResponseDto {
    private Integer clinicCode;
    private String patientCode;
    private String userCode;
    private Timestamp clinicDate;
    private String context;
    private Timestamp regDate;
    private Timestamp modDate;

    public ClinicResponseDto(Clinic clinic) {
        super.setStatusCode(HttpStatus.OK.value());
        super.setMessage("진료정보가 성공적으로 조회되었습니다.");
        this.clinicCode=clinic.getClinicCode();
        this.patientCode=clinic.getPatientCode();
        this.userCode=clinic.getUserCode();
        this.clinicDate=clinic.getClinicDate();
        this.context=clinic.getContext();
        this.regDate=clinic.getRegDate();
        this.modDate=clinic.getModDate();
    }
}
