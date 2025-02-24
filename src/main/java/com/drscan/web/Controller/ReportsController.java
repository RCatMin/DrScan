package com.drscan.web.Controller;

import com.drscan.web.secondary.patientScan.service.PatientScanService;
import com.drscan.web.secondary.series.domain.Series;
import com.drscan.web.secondary.series.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/reports")
@Controller
public class ReportsController {

    private final PatientScanService patientScanService;
    private final SeriesService seriesService;

    @GetMapping("/views/{patientId}/{studyKey}")
    public ResponseEntity<?> getPatientRecords(@PathVariable String patientId) {
        Map<String, Object> patientResponse = patientScanService.getPatientRecords(patientId);

        if (patientResponse.containsKey("error")){
            return ResponseEntity.badRequest().body(patientResponse);
        }
        return ResponseEntity.ok(patientResponse);
    }

    public ResponseEntity<?> getStudyRecords(@PathVariable Integer studyKey) {
        List<Series> seriesResponse = seriesService.findSeriesAll(studyKey);

        if (seriesResponse.isEmpty()){
            return ResponseEntity.badRequest().body(seriesResponse);
        }

        return ResponseEntity.ok(seriesResponse);
    }

}