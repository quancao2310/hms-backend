package com.example.hms.staffservice.common.service.impl;

import com.example.hms.staffservice.common.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendPasswordEmail(String email, String fullName, String password) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject("HMS Staff Account - Your New Password");
            message.setText(String.format(
                    "Dear %s,\n\n" +
                    "Your HMS staff account has been created.\n\n" +
                    "Your temporary password is: %s\n\n" +
                    "Please log in and change your password immediately.\n\n" +
                    "Regards,\nHMS Administration Team",
                    fullName, password
            ));
            
            mailSender.send(message);
            log.info("Password email sent successfully to {}", email);
        } catch (Exception e) {
            log.error("Failed to send password email to {}: {}", email, e.getMessage());
            throw new RuntimeException("Failed to send password email", e);
        }
    }
} 