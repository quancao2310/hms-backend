package com.example.hms.staffservice.staff.controller;

import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import com.example.hms.staffservice.staff.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    public ResponseEntity<StaffDTO> createStaff(@Valid @RequestBody Object dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(staffService.createStaff(dto));
    }

    @GetMapping
    public ResponseEntity<?> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable UUID id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffDTO> updateStaff(@PathVariable UUID id, @Valid @RequestBody Object dto) {
        return ResponseEntity.ok(staffService.updateStaff(id, dto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MessageDTO> updateStaffStatus(@PathVariable UUID id, @Valid @RequestBody Object dto) {
        return ResponseEntity.ok(staffService.updateStaffStatus(id, dto));
    }
}
