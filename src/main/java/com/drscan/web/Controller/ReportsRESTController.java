package com.drscan.web.Controller;

import com.drscan.web.secondary.image.domain.Image;
import com.drscan.web.secondary.patientScan.service.PatientScanService;
import com.drscan.web.secondary.series.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/reports")
@RestController
public class ReportsRESTController {

    private final PatientScanService patientScanService;

    @GetMapping("/checking/{studykey}/{serieskey}")
    public ResponseEntity<?> getImagesByStudyAndSeries(@PathVariable Integer studykey, @PathVariable Integer serieskey) {
        List<Image> patientReport = patientScanService.getImagesByStudyAndSeries(studykey, serieskey);

        return ResponseEntity.ok(patientReport);
    }

}