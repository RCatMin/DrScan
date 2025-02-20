package com.drscan.web.clinics.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.security.Timestamp;

@Getter
@Entity
@Table(name = "clinic", schema = "drscan_db")
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clinicCode;

    private String patientCode;
    private String userCode;
    private String clinicDate;
    private String context;
    private Timestamp regDate;
    private Timestamp modDate;
}
