package com.drscan.web.primary.users.util;

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
        // 이메일 인증 토큰 생성
        String token = tokenService.generateToken(toEmail);
        // 이메일 인증 링크 생성
        String verificationLink = "http://localhost:8080/verify?token=" + token;

        // 이메일 내용 설정
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject("Email Verification");
        helper.setText("Click the link to verify your email: " + verificationLink, true);

        // 이메일 발송
        emailSender.send(message);
    }
}