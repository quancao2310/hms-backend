package com.example.hms.staffservice.staffmanagement.controller;

import com.example.hms.staffservice.staffmanagement.dto.*;
import com.example.hms.staffservice.staffmanagement.service.AdminService;
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

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staff/admins")
@RequiredArgsConstructor
@Tag(name = "Admin Management", description = "API endpoints for admin operations")
public class AdminController {

    private final AdminService adminService;
    
    @Operation(summary = "Get all administrators", description = "Retrieves a paginated list of all administrators")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of administrators",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginatedResponse.class)))
    })
    @GetMapping
    public ResponseEntity<PaginatedResponse<AdminDTO>> getAllAdmins(
        @Parameter(hidden = true) Pageable pageable,
        @Parameter(description = "Page number (0-based)") @RequestParam(required = false) Integer page,
        @Parameter(description = "Page size") @RequestParam(required = false) Integer size,
        @Parameter(description = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.", example = "fullName,desc") @RequestParam(required = false) String[] sort) {
        return ResponseEntity.ok(PaginationUtil.toPaginatedResponse(adminService.getAllAdmins(pageable)));
    }
    
    @Operation(summary = "Get administrator by ID", description = "Retrieves a specific administrator by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved administrator"),
        @ApiResponse(responseCode = "404", description = "Administrator not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(
            @Parameter(description = "Administrator ID") @PathVariable UUID id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }
    
    @Operation(summary = "Create administrator", description = "Creates a new administrator account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Administrator successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(
            @Parameter(description = "Administrator creation request") @Valid @RequestBody AdminStaffCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdmin(request));
    }
    
    @Operation(summary = "Update administrator", description = "Updates an existing administrator's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrator successfully updated"),
        @ApiResponse(responseCode = "404", description = "Administrator not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(
            @Parameter(description = "Administrator ID") @PathVariable UUID id,
            @Parameter(description = "Updated administrator details") @Valid @RequestBody StaffUpdateRequest request) {
        return ResponseEntity.ok(adminService.updateAdmin(id, request));
    }
    
    @Operation(summary = "Delete administrator", description = "Deletes an administrator account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Administrator successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Administrator not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(
            @Parameter(description = "Administrator ID") @PathVariable UUID id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
