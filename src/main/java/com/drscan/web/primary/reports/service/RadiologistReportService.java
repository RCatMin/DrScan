package com.drscan.web.primary.reports.service;

import com.drscan.web.primary.reports.domain.RadiologistReport;
import com.drscan.web.primary.reports.domain.RadiologistReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RadiologistReportService {

    private final RadiologistReportRepository repository;

    // 특정 시리즈 UID 기준으로 판독 데이터 가져오기
    public List<RadiologistReport> getReportsBySeriesInsUid(String seriesInsUid) {
        return repository.findBySeriesInsUid(seriesInsUid);
    }

    // 판독 데이터 저장 (등록/수정)
    public RadiologistReport saveReport(RadiologistReport report) {
        report.setModDate(LocalDateTime.parse(LocalDateTime.now().toString()));
        if (report.getReportCode() == null) {
            report.setRegDate(LocalDateTime.parse(LocalDateTime.now().toString()));
        }
        return repository.save(report);
    }
}
