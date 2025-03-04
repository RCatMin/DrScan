package com.drscan.web.Controller;

import com.drscan.web.primary.users.util.ResponseDto;
import com.drscan.web.secondary.patientScan.domain.Patient;
import com.drscan.web.secondary.patientScan.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("patients/action/")
@RestController
public class PatientRestController {

    private final PatientService patientService;

    @GetMapping("/all")
    public ResponseEntity<?> getPatientAll(@RequestParam(required = false)Integer page){
        Pageable paging = PageRequest.of(page == null ? 0 : page - 1, 5, Sort.by(Sort.Direction.DESC, "pid"));

        List<Patient> list = patientService.findAll(paging);

        int totalPages = patientService.getTotalPages(paging);

        if(list.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "존재하지 않는 페이지입니다."));

        Map<String, Object> data = new HashMap<>();
        data.put("patients", list);
        data.put("totalPages", totalPages);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/{pid}")
    public ResponseEntity<?> getPatientByCode(@PathVariable String pid) {
        Patient patient = patientService.findPatientByPid(pid); // 인스턴스 사용

        if (patient == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "존재하지 않는 환자입니다."));
        }

        return ResponseEntity.ok(patient); // 객체 반환 시 Generic 타입을 `<?>`로 변경
    }

}
