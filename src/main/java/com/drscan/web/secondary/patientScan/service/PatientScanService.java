package com.drscan.web.secondary.patientScan.service;

import com.drscan.web.secondary.patientScan.domain.Patient;
import com.drscan.web.secondary.patientScan.domain.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientScanService {

    private final PatientRepository patientRepository;

    public List<Patient> getStudiesByPatientId(String pid) {
        return patientRepository.findByPid(pid);
    }


}
