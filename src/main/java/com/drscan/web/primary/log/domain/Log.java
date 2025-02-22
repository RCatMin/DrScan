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
    private long userCode;
    private long reportCode;
    private String event;
    private Timestamp eventDate;

    public Log(long userCode, long reportCode, String event) {
        this.userCode = userCode;
        this.reportCode = reportCode;
        this.event = event;
    }

    public void update(LogRequestDto logDto) {
        this.userCode = logDto.getUserCode();
        this.reportCode = logDto.getReportCode();
        this.event = logDto.getEvent();
    }
}
