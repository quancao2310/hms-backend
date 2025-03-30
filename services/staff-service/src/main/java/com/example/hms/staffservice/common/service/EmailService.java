package com.example.hms.staffservice.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    public void sendPasswordEmail(String toEmail, String fullName, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Your Hospital Management System Account");
        message.setText(String.format(
                "Dear %s,\n\n" +
                "Your account has been created in the Hospital Management System.\n\n" +
                "Your temporary password is: %s\n\n" +
                "Please login and change your password as soon as possible for security reasons.\n\n" +
                "Regards,\n" +
                "Hospital Management System Team",
                fullName, password));
        
        mailSender.send(message);
    }
} 