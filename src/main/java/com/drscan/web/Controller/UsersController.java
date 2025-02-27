package com.drscan.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@Controller
public class UsersController {

    @GetMapping("/signin")
    public String signin() { return "users/signin"; }

    @GetMapping("/signup")
    public String signup() { return "users/signup"; }

    @GetMapping("/me")
    public String me() { return "users/me"; }

    @GetMapping("/admin")
    public String admin() { return "users/admin"; }

}
