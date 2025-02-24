package com.drscan.web.Controller;

import com.drscan.web.primary.clinic.domain.Clinic;

import com.drscan.web.primary.clinic.service.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clinic")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @PostMapping
    public ResponseEntity<Clinic> crescateClinic(@RequestBody Clinic clinic) {
        Clinic savedClinic = clinicService.saveClinic(clinic);
        return ResponseEntity.ok(savedClinic);
    }

    @GetMapping("/{clinicCode}")
    public ResponseEntity<?> getClinic(@PathVariable Integer clinicCode) {
        return clinicService.getClinicById(clinicCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{clinicCode}")
    public ResponseEntity<Clinic> updateClinic(@PathVariable Integer clinicCode, @RequestBody Clinic clinic) {
        return ResponseEntity.ok(clinicService.updateClinic(clinicCode, clinic));
    }

    @DeleteMapping("/{clinicCode}")
    public ResponseEntity<Void> deleteClinic(@PathVariable Integer clinicCode) {
        clinicService.deleteClinic(clinicCode);
        return ResponseEntity.noContent().build();
    }
}
