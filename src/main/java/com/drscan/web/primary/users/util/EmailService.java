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

    private final JavaMailSender mailSender;

    // 이메일 인증을 위한 메서드
    public void sendVerificationEmail(String to, String subject, String token) {
        // 이메일 인증 링크를 직접 문자열로 작성
        String verificationLink = "http://localhost:8080/auth/verify?token=" + token;

        // 이메일 내용 (HTML 형식)
        String emailContent = "<html>" +
                "<body>" +
                "<h1>이메일 인증</h1>" +
                "<p>아래 링크를 클릭하여 이메일 인증을 완료하세요:</p>" +
                "<a href='" + verificationLink + "'>인증하기</a>" +
                "</body>" +
                "</html>";

        // MimeMessage 생성
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        try {
            helper.setTo(to);               // 수신자 이메일 설정
            helper.setSubject(subject);     // 이메일 제목 설정
            helper.setText(emailContent, true);  // 이메일 내용 설정 (HTML 형식)
            mailSender.send(message);       // 이메일 전송
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);  // 이메일 전송 실패시 예외 처리
        }
    }
}
