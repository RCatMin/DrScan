package com.drscan.web.secondary.patientScan.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, String> {
    List<Patient> findByPid(String pid);
}
