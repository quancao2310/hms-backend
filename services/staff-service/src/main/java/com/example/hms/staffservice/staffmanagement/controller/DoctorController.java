package com.example.hms.staffservice.staffmanagement.controller;

import com.example.hms.staffservice.staffmanagement.dto.DoctorCreationRequest;
import com.example.hms.staffservice.staffmanagement.dto.DoctorDTO;
import com.example.hms.staffservice.staffmanagement.dto.DoctorUpdateRequest;
import com.example.hms.staffservice.staffmanagement.dto.PaginatedResponse;
import com.example.hms.staffservice.staffmanagement.service.DoctorService;
import com.example.hms.staffservice.staffmanagement.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staff/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctor Management", description = "API endpoints for managing doctors")
public class DoctorController {

    private final DoctorService doctorService;
    
    @Operation(summary = "Get all doctors", description = "Retrieves a paginated list of all doctors with optional filtering")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of doctors",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginatedResponse.class)))
    })
    @GetMapping
    public ResponseEntity<PaginatedResponse<DoctorDTO>> getAllDoctors(
            @Parameter(hidden = true) Pageable pageable,
            @Parameter(description = "Page number (0-based)") @RequestParam(required = false) Integer page,
            @Parameter(description = "Page size") @RequestParam(required = false) Integer size,
            @Parameter(description = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.", example = "fullName,desc") @RequestParam(required = false) String[] sort,
            @Parameter(description = "Filter by department name") @RequestParam(required = false) String department,
            @Parameter(description = "Filter by specialization") @RequestParam(required = false) String specialization,
            @Parameter(description = "Filter by full name (case-insensitive, partial match)") @RequestParam(required = false) String fullName) {
        
        // Pass all filter parameters to allow simultaneous filtering
        Page<DoctorDTO> result = doctorService.getDoctorsWithFilters(department, specialization, fullName, pageable);
        return ResponseEntity.ok(PaginationUtil.toPaginatedResponse(result));
    }
    
    @Operation(summary = "Get doctor by ID", description = "Retrieves a specific doctor by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved doctor details",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DoctorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(
            @Parameter(description = "ID of the doctor to be obtained") @PathVariable UUID id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }
    
    @Operation(summary = "Create doctor", description = "Creates a new doctor account (Admin access required)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Doctor successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(
            @Parameter(description = "Doctor creation request") @Valid @RequestBody DoctorCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(request));
    }

    @Operation(summary = "Update doctor", description = "Updates an existing doctor's information (Admin access required)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Doctor successfully updated"),
        @ApiResponse(responseCode = "404", description = "Doctor not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(
            @Parameter(description = "Doctor ID") @PathVariable UUID id,
            @Parameter(description = "Updated doctor details") @Valid @RequestBody DoctorUpdateRequest request) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, request));
    }

    @Operation(summary = "Delete doctor", description = "Deletes a doctor account (Admin access required)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Doctor successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Doctor not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(
            @Parameter(description = "Doctor ID") @PathVariable UUID id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
