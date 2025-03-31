package com.example.hms.staffservice.staffmanagement.controller;

import com.example.hms.staffservice.staffmanagement.dto.NurseCreationRequest;
import com.example.hms.staffservice.staffmanagement.dto.NurseDTO;
import com.example.hms.staffservice.staffmanagement.dto.NurseUpdateRequest;
import com.example.hms.staffservice.staffmanagement.dto.PaginatedResponse;
import com.example.hms.staffservice.staffmanagement.service.NurseService;
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
@RequestMapping("/api/v1/staff/nurses")
@RequiredArgsConstructor
@Tag(name = "Nurse Management", description = "API endpoints for managing nurses")
public class NurseController {

    private final NurseService nurseService;
    
    @Operation(summary = "Get all nurses", description = "Retrieves a paginated list of all nurses")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of nurses",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginatedResponse.class)))
    })
    @GetMapping
    public ResponseEntity<PaginatedResponse<NurseDTO>> getAllNurses(
        @Parameter(hidden = true) Pageable pageable,
        @Parameter(description = "Page number (0-based)") @RequestParam(required = false) Integer page,
        @Parameter(description = "Page size") @RequestParam(required = false) Integer size,
        @Parameter(description = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.", example = "fullName,desc") @RequestParam(required = false) String[] sort,
        @Parameter(description = "Filter by full name (case-insensitive, partial match)") @RequestParam(required = false) String fullName,
        @Parameter(description = "Filter by qualification") @RequestParam(required = false) String qualification,
        @Parameter(description = "Filter by license number") @RequestParam(required = false) String licenseNumber) {
        
        // Pass all filter parameters to allow simultaneous filtering
        Page<NurseDTO> result = nurseService.getNursesWithFilters(fullName, qualification, licenseNumber, pageable);
        return ResponseEntity.ok(PaginationUtil.toPaginatedResponse(result));
    }
    
    @Operation(summary = "Get nurse by ID", description = "Retrieves a specific nurse by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved nurse details",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = NurseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Nurse not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NurseDTO> getNurseById(
            @Parameter(description = "ID of the nurse to be obtained") @PathVariable UUID id) {
        return ResponseEntity.ok(nurseService.getNurseById(id));
    }

    @Operation(summary = "Create nurse", description = "Creates a new nurse account (Admin access required)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Nurse successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public ResponseEntity<NurseDTO> createNurse(
            @Parameter(description = "Nurse creation request") @Valid @RequestBody NurseCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(nurseService.createNurse(request));
    }

    @Operation(summary = "Update nurse", description = "Updates an existing nurse's information (Admin access required)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nurse successfully updated"),
        @ApiResponse(responseCode = "404", description = "Nurse not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NurseDTO> updateNurse(
            @Parameter(description = "Nurse ID") @PathVariable UUID id,
            @Parameter(description = "Updated nurse details") @Valid @RequestBody NurseUpdateRequest request) {
        return ResponseEntity.ok(nurseService.updateNurse(id, request));
    }

    @Operation(summary = "Delete nurse", description = "Deletes a nurse account (Admin access required)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Nurse successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Nurse not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNurse(
            @Parameter(description = "Nurse ID") @PathVariable UUID id) {
        nurseService.deleteNurse(id);
        return ResponseEntity.noContent().build();
    }
}
