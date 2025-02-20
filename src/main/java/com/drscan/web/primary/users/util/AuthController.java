package com.drscan.web.primary.users.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    @PostMapping("/send-verification")
    public ResponseEntity<String> sendVerificationEmail(@RequestParam String email) {
        String token = jwtUtil.generateToken(email);
        redisService.saveToken(email, token, 5); // 5분 유지
        emailService.sendVerificationEmail(email, "이메일 인증", token);
        return ResponseEntity.ok("인증 이메일이 발송되었습니다.");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        String email = jwtUtil.validateToken(token);
        String storedToken = redisService.getToken(email);
        if (storedToken == null || !storedToken.equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
        }
        redisService.deleteToken(email);
        return ResponseEntity.ok("이메일 인증 성공");
    }
}

