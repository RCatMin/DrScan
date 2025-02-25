package com.drscan.web.Controller;

import com.drscan.web.secondary.patientScan.service.PatientScanService;
import com.drscan.web.secondary.series.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/reports")
@RestController
public class ReportsRESTController {

    private final PatientScanService patientScanService;

    @GetMapping("/views/{pId}")
    public ResponseEntity<?> getFullPatientReport(@PathVariable String pId) {
        Map<String, Object> patientReport = patientScanService.getPatientRecords(pId);

        if (patientReport.containsKey("error")) {
            return ResponseEntity.badRequest().body(patientReport);
        }

        return ResponseEntity.ok(patientReport);
    }

}