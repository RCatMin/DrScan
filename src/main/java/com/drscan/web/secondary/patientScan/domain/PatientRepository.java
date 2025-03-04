package com.drscan.web.secondary.patientScan.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface PatientRepository extends JpaRepository<Patient, String> {
    List<Patient> findByPid(String pid);
}
