package com.example.hms.staffservice.staff.controller;

import com.example.hms.staffservice.auth.security.impl.CustomUserDetails;
import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import com.example.hms.staffservice.staff.dto.UpdatePasswordDTO;
import com.example.hms.staffservice.staff.service.StaffAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/staff/account/profile")
@RequiredArgsConstructor
public class StaffAccountController {

    private final StaffAccountService staffAccountService;

    @GetMapping
    public ResponseEntity<StaffDTO> getProfile(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(staffAccountService.getProfile(user));
    }

    @PutMapping("/password")
    public ResponseEntity<MessageDTO> changePassword(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UpdatePasswordDTO dto) {
        return ResponseEntity.ok(staffAccountService.changePassword(user, dto));
    }
}
