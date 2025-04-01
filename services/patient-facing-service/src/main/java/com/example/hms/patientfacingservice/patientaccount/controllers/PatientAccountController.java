package com.example.hms.patientfacingservice.patientaccount.controllers;

import com.example.hms.patientfacingservice.auth.security.impl.CustomUserDetails;
import com.example.hms.patientfacingservice.common.dtos.MessageDTO;
import com.example.hms.patientfacingservice.patientaccount.dtos.PatientAccountDTO;
import com.example.hms.patientfacingservice.patientaccount.dtos.UpdateAccountDTO;
import com.example.hms.patientfacingservice.patientaccount.dtos.UpdatePasswordDTO;
import com.example.hms.patientfacingservice.patientaccount.services.PatientAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients/account")
@RequiredArgsConstructor
public class PatientAccountController {

    private final PatientAccountService patientAccountService;

    @GetMapping
    public ResponseEntity<PatientAccountDTO> getProfile(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(patientAccountService.getProfile(user));
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UpdateAccountDTO dto) {
        return ResponseEntity.ok(patientAccountService.updateUserDetails(user, dto));
    }

    @PutMapping("/password")
    public ResponseEntity<MessageDTO> changePassword(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UpdatePasswordDTO dto) {
        return ResponseEntity.ok(patientAccountService.changePassword(user, dto));
    }
}
