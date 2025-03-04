package com.drscan.web.secondary.patientScan.service;

import com.drscan.web.secondary.patientScan.domain.Patient;
import com.drscan.web.secondary.patientScan.domain.PatientSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PatientService {
    private final PatientSearchRepository patientSearchRepository;

    public List<Patient> findAll(Pageable pageable) {
        Page<Patient> page = patientSearchRepository.findAll(pageable);
        List<Patient> patients = page.getContent();
        return patients;
    }

    public int getTotalPages(Pageable pageable) {
        Page<Patient> page = patientSearchRepository.findAll(pageable);
        return page.getTotalPages();
    }
    public Patient findPatientByPid(String pid) {
       Optional<Patient> patient = patientSearchRepository.findById(pid);
       return patient.orElse(null);
    }


}
