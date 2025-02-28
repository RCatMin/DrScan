package com.drscan.web.Controller;

import com.drscan.web.secondary.image.domain.Image;
import com.drscan.web.secondary.patientScan.service.PatientScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;


import java.util.List;

@RequestMapping("/reports")
@Controller
@RequiredArgsConstructor
public class ReportsController {

    private final PatientScanService patientScanService;

    @GetMapping("/views/{pid}/{studykey}/{serieskey}")
    public String reportsPatient(@PathVariable String pid, @PathVariable Integer studykey, @PathVariable Integer serieskey) {

        return "reports/viewer";
    }

}
