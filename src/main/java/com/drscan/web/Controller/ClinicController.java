package com.drscan.web.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClinicController {

    @GetMapping("/clinic")
    public String clinic() {
        return "clinic";
    }
}
