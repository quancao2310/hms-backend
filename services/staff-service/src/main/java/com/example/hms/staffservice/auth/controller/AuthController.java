package com.example.hms.staffservice.auth.controller;

import com.example.hms.staffservice.auth.dto.SignInRequestDTO;
import com.example.hms.staffservice.auth.dto.SignInResponseDTO;
import com.example.hms.staffservice.auth.security.impl.CustomUserDetails;
import com.example.hms.staffservice.auth.service.AuthService;
import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import com.example.hms.staffservice.staff.dto.UpdatePasswordDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/staff/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDTO> signIn(@Valid @RequestBody SignInRequestDTO dto) {
        return ResponseEntity.ok(authService.signIn(dto));
    }

    @GetMapping("/account")
    public ResponseEntity<StaffDTO> getProfile(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(authService.getProfile(user));
    }

    @PutMapping("/account/password")
    public ResponseEntity<MessageDTO> changePassword(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UpdatePasswordDTO dto) {
        return ResponseEntity.ok(authService.changePassword(user, dto));
    }
}
