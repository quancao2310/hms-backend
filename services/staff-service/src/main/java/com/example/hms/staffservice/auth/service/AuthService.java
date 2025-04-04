package com.example.hms.staffservice.auth.service;

import com.example.hms.staffservice.auth.dto.SignInRequestDTO;
import com.example.hms.staffservice.auth.dto.SignInResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    SignInResponseDTO signIn(SignInRequestDTO dto);
}
