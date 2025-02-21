package com.drscan.web.primary.reports.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;


@Entity
@Getter
@Table (name = "seriestab", schema = "pacsplus")
public class SeriesList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seriesKey;
    private Integer studyKey;

    @Column(nullable = false)
    private String seriesInsUid;
    private String seriesName;
    private String modality;
    private Timestamp seriesDate;
    private Integer imageCnt;
}
