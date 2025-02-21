package com.drscan.web.secondary.series.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "seriestab", schema = "pacsplus")
@IdClass(SeriesId.class)
public class Series {

    @Id
    private Integer studyKey;

    @Id
    private Integer seriesKey;

    private String studyinsuid;

    @Column(unique = true)
    private String seriesinsuid;
    private Integer seriesnum;
    private String modality;
    private String seriesdate;
    private String seriestime;
    private String bodypart;
    private String seriesdesc;
    private Integer imagecnt;
    private Integer delflag;


}
