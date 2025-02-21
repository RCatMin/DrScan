package com.drscan.web.primary.reports.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "patienttab", schema = "pacsplus")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String patientId;

    private String patientBirthday;
    private String patientSex;
    private String patientName;

}
