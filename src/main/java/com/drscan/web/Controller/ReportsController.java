package com.drscan.web.Controller;

import com.drscan.web.primary.reports.service.ReportsService;
import com.drscan.web.primary.users.domain.User;
import com.drscan.web.primary.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/reports")
@Controller
public class ReportsController {

    private final ReportsService reportsService;

    @GetMapping("/views/{patientId}")
    public ResponseEntity<?> findPatientAll(){
        return ResponseEntity.ok("");
    }
}