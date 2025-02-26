package com.drscan.web.primary.log.domain;

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
    private long userCode;

    @Column(name = "report_code")
    private long reportCode;
    private String event;

    @Column(name = "event_date")
    private Timestamp eventDate;

    public Log(long userCode, String event) {
        this.userCode = userCode;
        this.event = event;
    }

    public Log(long userCode, long reportCode, String event) {
        this.userCode = userCode;
        this.reportCode = reportCode;
        this.event = event;
    }
}
