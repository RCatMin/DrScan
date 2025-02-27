package com.drscan.web.Controller;

import com.drscan.web.primary.clinic.domain.Clinic;
import com.drscan.web.primary.clinic.domain.ClinicResponseDto;
import com.drscan.web.primary.clinic.service.ClinicService;
import com.drscan.web.primary.users.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/clinic/action")
@RequiredArgsConstructor
public class ClinicRestController {
    private final ClinicService clinicService;

    // 진료 생성
    @PostMapping
    public ResponseEntity<String> createClinic(@RequestBody Clinic clinic) {
        String savedClinic = clinicService.saveClinic(clinic);
        return ResponseEntity.ok(savedClinic);
    }


    // patient_code로 진료 조회
    @GetMapping("/{patientCode}")
    public ResponseEntity<?> getClinic(@PathVariable String patientCode) {
        List<Clinic> clinics = clinicService.getClinicByPatientCode(patientCode);

        if(clinics.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "진료정보가 존재하지 않습니다."));
        }
        return ResponseEntity.ok(clinics);

    }
    // 진료 상세 조회
    @GetMapping("/detail/{clinicCode}")
    public ResponseEntity<?> getClinicByCode(@PathVariable Long clinicCode) {
        Clinic clinic = clinicService.getClinicByClinicCode(clinicCode);

        if(clinic == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND.value())
                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "존재하지 않는 게시물입니다."));
        }

        ClinicResponseDto clinicDto = new ClinicResponseDto(clinic);
        System.out.println("진료 상세 데이터: " + clinicDto); // 로그 출력 추가
        return ResponseEntity.ok(clinicDto);
    }


    // 진료 목록수정
    @PutMapping("/detail/{clinicCode}")
    public ResponseEntity<Clinic> updateClinic(@PathVariable Long clinicCode, @RequestBody Clinic clinic) {
        return ResponseEntity.ok(clinicService.updateClinic(clinicCode, clinic));
    }

    // 진료 삭제
    @DeleteMapping("/{clinicCode}")
    public ResponseEntity<ResponseDto> deleteClinicByCode(@PathVariable Long clinicCode) {
        boolean isSuccess = clinicService.deleteClinic(clinicCode);
        if(!isSuccess)
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "존재하지 않는 진료기록입니다."));
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "진료 삭제가 완료되었습니다."));
    }
}
