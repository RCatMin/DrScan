package com.drscan.web.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/patientScan")
@RestController
public class PatientScanRestController {

    @GetMapping("/search")
    public String search() { return "patient-imaging-record/patient-search"; }

    @GetMapping("/histiry")
    public String histiry() { return "patient-imaging-record/scan-history"; }

}
