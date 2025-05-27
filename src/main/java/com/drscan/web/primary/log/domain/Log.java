package com.drscan.web.primary.log.domain;

import com.drscan.web.primary.reports.domain.RadiologistReport;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;

    @Column(name = "user_code")
    private Long userCode;

    @Column(name = "report_code")
    private Long reportCode;

    private String event;

    @Column(name = "event_date")
    private Timestamp eventDate;

    @Column(name = "clinic_code")
    private Long clinicCode;

    @PrePersist
    public void prePersist() {
        if (this.eventDate == null) {
            this.eventDate = new Timestamp(System.currentTimeMillis());
        }
    }

    public Log(long userCode, String event) {
        this.userCode = userCode;
        this.event = event;
    }

    public Log(long userCode, RadiologistReport report, String event) {
        this.userCode = userCode;
        this.reportCode = Long.valueOf(report.getReportCode());
        this.event = event;
    }

    public Log(long userCode, long clinicCode, String event) {
        this.userCode = userCode;
        this.clinicCode = clinicCode;
        this.event = event;
    }

}
