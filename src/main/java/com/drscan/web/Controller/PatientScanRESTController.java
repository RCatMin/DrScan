package com.drscan.web.Controller;

import com.drscan.web.secondary.patientScan.service.PatientScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/patient/action")
@RestController
public class PatientScanRESTController {

    private final PatientScanService patientScanService;

    @GetMapping("/{pid}")
    public ResponseEntity<?> getPatientStudies(@PathVariable String pid) {
        return ResponseEntity.ok(patientScanService.getStudiesByPatientId(pid));
    }
}
