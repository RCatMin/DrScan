package com.drscan.web.primary.reports.service;

import com.drscan.web.primary.reports.domain.RadiologistReport;
import com.drscan.web.primary.reports.domain.RadiologistReportRepository;
import com.drscan.web.secondary.patientScan.domain.PatientRepository;
import com.drscan.web.secondary.series.domain.SeriesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RadiologistReportService {

    private final RadiologistReportRepository radiologistReportRepository;

    //    // 특정 시리즈 UID 기준으로 판독 데이터 가져오기
    public RadiologistReport getLatestReportByPatientId(String patientId) {
        return radiologistReportRepository.findLatestReportByPatientId(patientId);
    }

//    // 판독 데이터 저장 (등록/수정)
    public RadiologistReport saveReport(RadiologistReport report) {
        report.setModDate(LocalDateTime.parse(LocalDateTime.now().toString()));
        if (report.getReportCode() == null) {
            report.setRegDate(LocalDateTime.parse(LocalDateTime.now().toString()));
        }
        return radiologistReportRepository.save(report);
    }


    // 환자 ID로 판독 기록 조회
    public List<RadiologistReport> getReportsByPatientId(String patientId) {
        return radiologistReportRepository.findByPatientId(patientId);
    }
}
