package com.drscan.web.primary.reports.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table (name = "imagetab", schema = "pacsplus")
public class ImageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seriesKey;
    private Integer studyKey;

    private String sopInstanceUid;
    private String modality;
    private String seriesDate;
    private String bodyPart;
    private Integer imageCnt;
}
