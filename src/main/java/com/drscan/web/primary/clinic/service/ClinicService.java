package com.drscan.web.primary.clinic.service;

import com.drscan.web.primary.clinic.domain.Clinic;
import com.drscan.web.primary.clinic.domain.ClinicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClinicService {

    private final ClinicRepository clinicRepository;

    // 진료 내용 생성
    public String saveClinic(Clinic clinic) {

        clinicRepository.save(clinic);
        return "진료 등록 성공";
    }


    // 환자 id로 진료 전체 내용 조회
    public List<Clinic> getClinicByPatientCode(String patientCode) {

        return clinicRepository.findClinicByPatientCode(patientCode);
    }

    // 진료코드로 진료내용 조회
    public Clinic getClinicByClinicCode(Long clinicCode) {
        return clinicRepository.findClinicByClinicCode(clinicCode);
    }

    // 진료 내용 업데이트
    public Clinic updateClinic(Long clinicCode, Clinic updatedClinic) {
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
    @Transactional
    public boolean deleteClinic(Long clinicCode) {
        Optional<Clinic> target = clinicRepository.findById(clinicCode);
        Clinic clinic = target.orElse(null);

        if(clinic == null) {
            return false;
        }

        clinicRepository.delete(clinic);
        return true;
    }


}
