package com.drscan.web.primary.clinic.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "clinic", schema = "drscan_db")
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clinicCode;


    private String patientCode;
    private String userCode;
    private Timestamp clinicDate;
    private String context;
    private Timestamp regDate;
    private Timestamp modDate;
}
