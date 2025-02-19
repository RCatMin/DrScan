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

    @GetMapping("/users/signin")
    public String signin() { return "users/signin"; }

    @GetMapping("/users/signup")
    public String signup() { return "users/signup"; }
}
