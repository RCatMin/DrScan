package com.drscan.web.secondary.patientScan.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientScanRepository extends JpaRepository<Patient, String> {
    List<Patient> findByPid(String pid);
}