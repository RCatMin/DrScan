package com.drscan.web.Controller;

import com.drscan.web.secondary.patientScan.service.PatientScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/reports/viewer")
@Controller
@RequiredArgsConstructor
public class ReportsController {

    private final PatientScanService patientScanService;

    @GetMapping("/{studykey}/{serieskey}")
    public String dcmViewer(@PathVariable String studykey,
                            @PathVariable String serieskey) {
        return "reports/viewer";
    }

}
