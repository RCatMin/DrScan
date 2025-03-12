package com.drscan.web.primary.reports.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RadiologistReportRepository extends JpaRepository<RadiologistReport, Integer> {
    @Query("SELECT r FROM RadiologistReport r WHERE r.patientId = :patientId ORDER BY r.modDate DESC LIMIT 1")
    RadiologistReport findLatestReportByPatientId(@Param("patientId") String patientId);

    List<RadiologistReport> findByPatientId(String patientId);
}
