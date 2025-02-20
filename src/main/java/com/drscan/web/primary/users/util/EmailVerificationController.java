package com.drscan.web.primary.users.util;

import org.springframework.web.bind.annotation.*;

@RestController
public class EmailVerificationController {

    private final TokenService tokenService;
    private final EmailService emailService;

    public EmailVerificationController(TokenService tokenService, EmailService emailService) {
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    // 이메일 인증 요청
    @PostMapping("/send-verification")
    public String sendVerificationEmail(@RequestBody String email) {
        try {
            emailService.sendVerificationEmail(email);
            return "Verification email sent to " + email;
        } catch (Exception e) {
            return "Failed to send verification email: " + e.getMessage();
        }
    }

    // 이메일 인증 처리
    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token) {
        try {
            // 토큰에서 이메일 추출
            String email = tokenService.getEmailFromToken(token);
            // 토큰 검증
            if (tokenService.isTokenExpired(token)) {
                return "Token has expired!";
            }
            // 이메일 인증 성공 처리
            return "Email " + email + " has been successfully verified!";
        } catch (Exception e) {
            return "Invalid token: " + e.getMessage();
        }
    }
}
