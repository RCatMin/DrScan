package com.drscan.web.Controller;

import com.drscan.web.users.domain.User;
import com.drscan.web.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users/action")
@RestController
public class UsersRestController {

    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<List<User>> findUserAll(){
        return ResponseEntity.ok(userService.findUserAll());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(){
        return ResponseEntity.ok("");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(){
        return ResponseEntity.ok("");
    }
}
