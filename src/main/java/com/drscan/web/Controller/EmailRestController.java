package com.drscan.web.Controller;

import com.drscan.web.primary.users.domain.User;
import com.drscan.web.primary.users.service.EmailService;
import com.drscan.web.primary.users.service.TokenService;
import com.drscan.web.primary.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EmailRestController {

    private final TokenService tokenService;
    private final EmailService emailService;
    private final UserService userService;

    public EmailRestController(TokenService tokenService, EmailService emailService, UserService userService) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/send-verification")
    public ResponseEntity<Map<String, String>> sendVerificationEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if(email == null) {
            String username = request.get("username");
            User user = userService.findUserByUsername(username);
            email = user.getEmail();
        }

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
    public String verifyEmail(@RequestParam String token, HttpServletRequest request) {
        try {

            String email = tokenService.getEmailFromToken(token);
            String code = tokenService.getVerificationCodeFromToken(token);

            HttpSession session = request.getSession();
            session.setAttribute("authCode", code);

            if (tokenService.isTokenExpired(token)) {
                return "Token has expired!";
            }
            return "<html>" +
                    "<head><title>Email Verification</title></head>" +
                    "<body style='font-family: Arial, sans-serif; text-align: center; padding: 20px;'>" +
                    "<h2 style='color: #4CAF50;'>Email Verification Successful!</h2>" +
                    "<p style='font-size: 16px;'>The email <strong>" + email + "</strong> has been successfully verified.</p>" +
                    "<p style='font-size: 18px; font-weight: bold;'>Your verification code:</p>" +
                    "<p style='font-size: 24px; font-weight: bold; color: #ff5722;'>" + code + "</p>" +
                    "<p style='font-size: 14px; color: #555;'>You can now proceed with the next step.</p>" +
                    "</body>" +
                    "</html>";

        } catch (Exception e) {
            return "Invalid token: " + e.getMessage();
        }
    }
}
