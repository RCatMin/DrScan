//package com.drscan.web.secondary.reports.domain;
//
//import com.drscan.web.secondary.series.domain.SeriesId;
//import jakarta.persistence.*;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//
//@Getter
//@Entity
//@Table(name = "seriestab", schema = "pacsplus")
//@IdClass(SeriesId.class)
//@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
//public class DicomSeries {
//
//    @Id
//    private Integer studykey;
//
//    @Id
//    private Integer serieskey;
//
//    private String studyinsuid;
//
//    @Column(unique=true)
//    private String seriesinsuid;
//    private String seriesnum;
//    private String modality;
//    private String seriesdate;
//    private String seriestime;
//    private String bodypart;
//    private String seriesdesc;
//    private Integer imagecnt;
//    private Integer delflag;
//
//    public DicomSeries(Integer serieskey, String modality) {
//        this.serieskey = serieskey;
//        this.modality = modality;
//    }
//}
//
