package com.drscan.web.primary.reports.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RadiologistReportRepository extends JpaRepository<RadiologistReport, Integer> {
    List<RadiologistReport> findBySeriesInsUid(String seriesInsUid);

    List<RadiologistReport> findByPatientId(String patientId);
}
