package com.drscan.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/header")
    public String header() {
        return "module/header";
    }

    @GetMapping("/footer")
    public String footer() {
        return "module/footer";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/admin")
    public String admin() {
        return "users/admin";
    }

    @GetMapping({"/admin/logs", "/admin/log"})
    public String adminLog() {return "/log/log";}
}
