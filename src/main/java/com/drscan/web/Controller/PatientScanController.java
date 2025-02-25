package com.drscan.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/patientScan")
@Controller
public class PatientScanController {

    @GetMapping("/search")
    public String search() { return "patient-imaging-record/patient-search"; }

    @GetMapping("/imaging-record/{pid}/{studykey}/{serieskey}")
    public String imagingRecordPage(@PathVariable String pid,
                                    @PathVariable Integer studykey,
                                    @PathVariable Integer serieskey) {
        return "patient-imaging-record/imaging-record";
    }
}
