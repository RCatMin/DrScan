package com.drscan.web.Controller;

import com.drscan.web.primary.clinic.domain.Clinic;

import com.drscan.web.primary.clinic.service.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clinic")
public class ClinicController {

   @GetMapping({"/",""})
    public String clinic() {
       return "clinic/clinic";
   }

   @GetMapping("/add")
   public String addClinic(){
       return "clinic/addClinic";
   }

    @GetMapping("/detail")
    public String clinicDetail(){
        return "";
    }
}
