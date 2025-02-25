package com.drscan.web.primary.clinic.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Setter
@Getter
@Entity
@Table(name = "clinic", schema = "drscan_db")
public class Clinic extends com.drscan.web.primary.users.util.Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="clinic_code")
    private Long clinicCode;

    @Column(name="patient_code")
    private String patientCode;
    @Column(name="user_code")
    private String userCode;
    @Column(name="clinic_date")
    private Timestamp clinicDate;
    private String context;

    public void update(ClinicRequestDto clinicDto){
        this.clinicDate = clinicDto.getClinicDate();
        this.context = clinicDto.getContext();
    }

}
