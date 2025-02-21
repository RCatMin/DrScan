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

        String htmlContent = "<html>"
                + "<head>"
                + "<style>"
                + "    body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f7fc; }"
                + "    .container { width: 100%; max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }"
                + "    h2 { color: #333333; font-size: 24px; text-align: center; margin-bottom: 20px; }"
                + "    p { font-size: 16px; color: #666666; line-height: 1.6; margin-bottom: 20px; }"
                + "    .btn { display: block; width: 100%; text-align: center; padding: 14px 0; background-color: #4CAF50; color: white; text-decoration: none; font-size: 18px; border-radius: 5px; margin-top: 20px; }"
                + "    .footer { font-size: 12px; color: #aaaaaa; text-align: center; margin-top: 30px; }"
                + "    .footer a { color: #4CAF50; text-decoration: none; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "    <h2>Email Verification</h2>"
                + "    <p>Hi there,</p>"
                + "    <p>We received a request to verify your email address. Please click the button below to complete the verification process:</p>"
                + "    <a href='" + verificationLink + "' class='btn'>Verify Email</a>"
                + "    <p>If you did not request this, you can safely ignore this email.</p>"
                + "    <div class='footer'>"
                + "        <p>Thank you for using our service!</p>"
                + "        <p><a href='#'>Unsubscribe</a> from our emails at any time.</p>"
                + "    </div>"
                + "</div>"
                + "</body>"
                + "</html>";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject("Email Verification");
        helper.setText(htmlContent, true);

        emailSender.send(message);
    }
}