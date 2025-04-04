package com.example.hms.staffservice.auth.controller;

import com.example.hms.staffservice.auth.dto.SignInRequestDTO;
import com.example.hms.staffservice.auth.dto.SignInResponseDTO;
import com.example.hms.staffservice.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/staff/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDTO> signIn(@Valid @RequestBody SignInRequestDTO dto) {
        return ResponseEntity.ok(authService.signIn(dto));
    }
}
