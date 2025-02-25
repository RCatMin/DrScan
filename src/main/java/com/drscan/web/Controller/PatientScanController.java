package com.drscan.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/patientScan")
@Controller
public class PatientScanController {

    @GetMapping("/search")
    public String search() { return "patient-imaging-record/patient-search"; }
}
