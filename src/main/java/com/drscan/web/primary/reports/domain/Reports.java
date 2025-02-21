package com.drscan.web.primary.reports.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.security.Timestamp;

@Entity
@Getter
@Table (name = "seriestab", schema = "pacsplus")
public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportCode;

    private String seriesInsUid;
    private String patientId;
    private Integer userCode;
    private Integer approveUserCode;
    private Timestamp studyDate;
    private Timestamp approveStudyDate;
    private String studyName;
    private String modality;
    private String bodyPart;
    private String patientName;
    private enum patientSex{
        MALE, FEMALE
    }
    private Timestamp patientBirthDate;
    private char patientAge;
    private enum reportStatus{
        Draft, Finalized, NeedsRevsion
    }
    private Timestamp regDate;
    private Timestamp modDate;
}