package com.drscan.web.secondary.patientScan.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientScanRepository extends JpaRepository<Patient, Integer> {

    //List<Patient> findByPid(String pid);

    Optional<Patient> findByPid(String pid);
    // 다시확인하기
}
