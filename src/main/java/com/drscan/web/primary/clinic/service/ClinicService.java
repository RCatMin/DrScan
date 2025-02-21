package com.drscan.web.clinic.service;

import com.drscan.web.clinic.domain.Clinic;
import com.drscan.web.clinic.domain.ClinicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClinicService {

    private final ClinicRepository clinicRepository;

    // 진료 내용 저장
    public Clinic saveClinic(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    // 모든 진료 내용 조회
    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll();
    }

    // 특정 진료 내용 조회
    public Optional<Clinic> getClinicById(Integer clinicCode) {
        return clinicRepository.findById(clinicCode);
    }

    // 진료 내용 업데이트
    public Clinic updateClinic(Integer clinicCode, Clinic updatedClinic) {
        return clinicRepository.findById(clinicCode)
                .map(clinic -> {
                    clinic.setClinicDate(updatedClinic.getClinicDate());
                    clinic.setContext(updatedClinic.getContext());
                    clinic.setModDate(new Timestamp(System.currentTimeMillis()));
                    return clinicRepository.save(clinic);
                })
                .orElseThrow(() -> new RuntimeException("Clinic not found with id: " + clinicCode));
    }

    // 진료 내용 삭제
    public void deleteClinic(Integer clinicCode) {
        clinicRepository.deleteById(clinicCode);
    }
}
