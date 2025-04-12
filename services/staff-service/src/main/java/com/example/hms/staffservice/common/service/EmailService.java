package com.example.hms.staffservice.common.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendPasswordEmail(String email, String fullName, String password);
}
