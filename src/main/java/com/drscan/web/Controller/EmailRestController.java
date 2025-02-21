package com.drscan.web.Controller;

import com.drscan.web.primary.users.service.EmailService;
import com.drscan.web.primary.users.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EmailRestController {

    private final TokenService tokenService;
    private final EmailService emailService;

    public EmailRestController(TokenService tokenService, EmailService emailService) {
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @PostMapping("/send-verification")
    public ResponseEntity<Map<String, String>> sendVerificationEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Map<String, String> response = new HashMap<>();

        try {
            emailService.sendVerificationEmail(email);
            response.put("message", "Verification email sent");
            response.put("email", email);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to send verification email");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token) {
        try {

            String email = tokenService.getEmailFromToken(token);

            if (tokenService.isTokenExpired(token)) {
                return "Token has expired!";
            }

            return "Email " + email + " has been successfully verified!";
        } catch (Exception e) {
            return "Invalid token: " + e.getMessage();
        }
    }
}
