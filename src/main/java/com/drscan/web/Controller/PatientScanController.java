package com.drscan.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/patientScan")
@Controller
public class PatientScanController {

    @GetMapping("/list")
    public String list() { return "patient-imaging-record/viewerList"; }

    @GetMapping("/imaging-record/{pid}/{studykey}/{serieskey}")
    public String imagingRecordPage(@PathVariable String pid,
                                    @PathVariable Integer studykey,
                                    @PathVariable Integer serieskey) {
        return "patient-imaging-record/imaging-record";
    }

    @GetMapping("/radiology/{pid}")
    public String radiologistReports(@PathVariable String pid) {
        return "report/radiologistReports";
    }

    @GetMapping("/report-detail/{reportCode}")
    public String reportDetail(@PathVariable Integer reportCode) {
        return "report/reportDetail";
    }
}

