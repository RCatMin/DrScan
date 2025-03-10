package com.drscan.web.secondary.reports.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "studytab", schema = "pacsplus", uniqueConstraints = @UniqueConstraint(columnNames = {"studyinsuid", "pid"}))
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class DicomStudy {
    @Id
    private Integer studykey;

    @Column(nullable = false)
    private String studyinsuid;
    private String studydate;
    private String studyid;

    @Column(length = 256)
    private String studydesc;

    private String modality;

    @Column(nullable = false)
    private String pid;
    private String pname;
    private String psex;
    private String pbirthdatetime;
    private String patage;
    private Integer seriescnt;
    private Integer imagecnt;
    private Integer delflag;

    public DicomStudy(String studydate, Integer studykey, String pid) {
        this.studydate = studydate;
        this.studykey = studykey;
        this.pid = pid;
    }
}
