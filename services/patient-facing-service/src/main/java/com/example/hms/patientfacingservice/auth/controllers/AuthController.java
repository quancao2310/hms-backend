package com.example.hms.patientfacingservice.auth.controllers;

import com.example.hms.patientfacingservice.auth.dtos.SignInRequestDTO;
import com.example.hms.patientfacingservice.auth.dtos.SignInResponseDTO;
import com.example.hms.patientfacingservice.auth.dtos.SignUpRequestDTO;
import com.example.hms.patientfacingservice.auth.services.AuthService;
import com.example.hms.patientfacingservice.patientaccount.dtos.PatientAccountDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patients/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<PatientAccountDTO> signUp(@Valid @RequestBody SignUpRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(dto));
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDTO> signIn(@Valid @RequestBody SignInRequestDTO dto) {
        return ResponseEntity.ok(authService.signIn(dto));
    }
}
