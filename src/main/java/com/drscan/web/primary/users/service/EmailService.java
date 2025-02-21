package com.drscan.web.primary.users.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final TokenService tokenService;

    public void sendVerificationEmail(String toEmail) throws MessagingException {

        String token = tokenService.generateToken(toEmail);

        String verificationLink = "http://localhost:8080/verify?token=" + token;

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject("Email Verification");
        helper.setText("Click the link to verify your email: " + verificationLink, true);

        emailSender.send(message);
    }
}