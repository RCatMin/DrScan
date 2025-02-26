package com.drscan.web.primary.clinic.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    public List<Clinic> findClinicByPatientCode(String patientCode);
    public Clinic findClinicByClinicCode(Long clinicCode);
}
