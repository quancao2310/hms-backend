package com.example.hms.staffservice.staff.controller;

import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.common.dto.PageResponse;
import com.example.hms.staffservice.staff.dto.*;
import com.example.hms.staffservice.staff.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
@Tag(name = "Staff Management", description = "API for managing hospital staff")
public class StaffController {

    private final StaffService staffService;

    @Operation(
            summary = "Create a new staff member",
            description = "Creates a new staff member with the provided details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Staff created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<StaffDTO> createStaff(@Valid @RequestBody CreateStaffDTO dto) {
        StaffDTO createdStaff = staffService.createStaff(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStaff);
    }

    @Operation(
            summary = "Get all staff with filtering and pagination",
            description = "Retrieves all staff members with optional filtering and pagination"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved staff list"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping
    public ResponseEntity<PageResponse<StaffDTO>> getAllStaff(@Valid @ModelAttribute StaffFilterDTO filterDTO) {
        return ResponseEntity.ok(staffService.getFilteredStaff(filterDTO));
    }

    @Operation(
            summary = "Get staff by ID",
            description = "Retrieves a staff member by their unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved staff"),
            @ApiResponse(responseCode = "404", description = "Staff not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StaffDTO> getStaffById(
            @Parameter(description = "Staff ID", required = true) @PathVariable UUID id
    ) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @Operation(
            summary = "Update staff information",
            description = "Updates an existing staff member's information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Staff updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Staff not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StaffDTO> updateStaff(
            @Parameter(description = "Staff ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody UpdateStaffDTO dto
    ) {
        return ResponseEntity.ok(staffService.updateStaff(id, dto));
    }

    @Operation(
            summary = "Partially update staff information",
            description = "Updates specific fields of an existing staff member's information using PATCH."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Staff partially updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Staff not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<StaffDTO> patchStaff(
            @Parameter(description = "Staff ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody PatchStaffDTO dto
    ) {
        return ResponseEntity.ok(staffService.patchStaff(id, dto));
    }

    @Operation(
            summary = "Update staff working status",
            description = "Updates a staff member's working status (ACTIVE, INACTIVE, ON_LEAVE, etc.)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Staff status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Staff not found")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<MessageDTO> updateStaffStatus(
            @Parameter(description = "Staff ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody UpdateStatusDTO dto
    ) {
        return ResponseEntity.ok(staffService.updateStaffStatus(id, dto));
    }
}
