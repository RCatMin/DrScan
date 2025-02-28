package com.drscan.web.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clinic")
public class ClinicController {

   @GetMapping("/{patientCode}")
    public String clinic(@PathVariable String patientCode) {
       return "clinic/clinic";
   }

   @GetMapping("/add/{patientCode}")
   public String addClinic(@PathVariable String patientCode){
       return "clinic/addClinic";
   }

    @GetMapping("/detail/{clinicCode}")
    public String clinicDetail(@PathVariable Long clinicCode) {
        return "clinic/clinicDetail";
    }
}
