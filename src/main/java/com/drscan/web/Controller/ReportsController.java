package com.drscan.web.Controller;

import com.drscan.web.secondary.patientScan.service.PatientScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;


import java.util.Map;

@RequestMapping("/reports")
@Controller
@RequiredArgsConstructor
public class ReportsController {

    private final PatientScanService patientScanService;

    @GetMapping("/views/test/{pId}")
    public String viewPatientReport(@PathVariable String pId, Model model) {
        Map<String, Object> patientReport = patientScanService.getPatientRecords(pId);

        if (patientReport.containsKey("error")) {
            model.addAttribute("error", patientReport.get("error"));
            return "error";  // 오류 페이지로 이동
        }

        System.out.println("환자 정보 : " + patientReport);
        model.addAttribute("patientReport", patientReport);
        return "reports/reports";
    }

}
