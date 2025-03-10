package com.drscan.web.Controller;

import com.drscan.web.secondary.patientScan.service.PatientScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/reports")
@Controller
@RequiredArgsConstructor
public class ReportsController {

    private final PatientScanService patientScanService;

    @GetMapping("/viewer/{studydate}/{studykey}/{pid}/{modality}/{serieskey}")
    public String reportsPatient(@PathVariable String studydate, @PathVariable Integer studykey, @PathVariable String pid, @PathVariable String modality ,@PathVariable Integer serieskey) {

        return "reports/viewer";
    }

}
