package com.drscan.web.primary.clinic.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
    public Clinic findClinicByPatientCode(String patientCode);
    public Clinic findClinicByClinicCode(Integer clinicCode);
}
