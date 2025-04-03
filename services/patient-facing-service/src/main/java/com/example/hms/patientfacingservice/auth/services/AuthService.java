package com.example.hms.patientfacingservice.auth.services;

import com.example.hms.patientfacingservice.auth.dtos.SignInRequestDTO;
import com.example.hms.patientfacingservice.auth.dtos.SignInResponseDTO;
import com.example.hms.patientfacingservice.auth.dtos.SignUpRequestDTO;
import com.example.hms.patientfacingservice.patientaccount.dtos.PatientAccountDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    PatientAccountDTO signUp(SignUpRequestDTO dto);

    SignInResponseDTO signIn(SignInRequestDTO dto);
}
