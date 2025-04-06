package com.example.hms.staffservice.staff.controller;

import com.example.hms.enums.WorkingStatus;
import com.example.hms.staffservice.common.dto.ApiResponse;
import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.common.dto.PageResponse;
import com.example.hms.staffservice.staff.dto.CreateStaffDTO;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import com.example.hms.staffservice.staff.dto.StaffFilterDTO;
import com.example.hms.staffservice.staff.dto.UpdateStaffDTO;
import com.example.hms.staffservice.staff.dto.UpdateStatusDTO;
import com.example.hms.staffservice.staff.dto.PatchStaffDTO;
import com.example.hms.staffservice.staff.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@Tag(name = "Staff Management", description = "API for managing hospital staff")
public class StaffController {

    private final StaffService staffService;

    @Operation(
        summary = "Create a new staff member",
        description = "Creates a new staff member with the provided details"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Staff created successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<StaffDTO>> createStaff(@Valid @RequestBody CreateStaffDTO dto) {
        try {
            StaffDTO createdStaff = staffService.createStaff(dto);
            return new ResponseEntity<>(
                    new ApiResponse<>("SUCCESS", "Staff created successfully", createdStaff),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("ERROR", e.getMessage(), null)
            );
        }
    }

    @Operation(
        summary = "Get all staff with filtering and pagination",
        description = "Retrieves all staff members with optional filtering and pagination"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved staff list"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<StaffDTO>>> getAllStaff(
            @Parameter(description = "Filter by staff full name") @RequestParam(required = false) String fullName,
            @Parameter(description = "Filter by working status") @RequestParam(required = false) WorkingStatus status,
            @Parameter(description = "Filter by department") @RequestParam(required = false) String department,
            @Parameter(description = "Field to sort by") @RequestParam(required = false, defaultValue = "fullName") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @Parameter(description = "Page number") @RequestParam(required = false, defaultValue = "0") Integer page,
            @Parameter(description = "Number of items per page") @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        try {
            StaffFilterDTO filterDTO = new StaffFilterDTO(
                    fullName, status, department, sortBy, sortDirection, page, size
            );
            PageResponse<StaffDTO> staffPage = staffService.getFilteredStaff(filterDTO);
            return ResponseEntity.ok(
                    new ApiResponse<>("SUCCESS", "Staff list retrieved successfully", staffPage)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("ERROR", e.getMessage(), null)
            );
        }
    }

    @Operation(
        summary = "Get staff by ID",
        description = "Retrieves a staff member by their unique identifier"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved staff"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Staff not found or error occurred")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StaffDTO>> getStaffById(
            @Parameter(description = "Staff ID", required = true) @PathVariable UUID id
    ) {
        try {
            StaffDTO staff = staffService.getStaffById(id);
            return ResponseEntity.ok(
                    new ApiResponse<>("SUCCESS", "Staff retrieved successfully", staff)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("ERROR", e.getMessage(), null)
            );
        }
    }

    @Operation(
        summary = "Update staff information",
        description = "Updates an existing staff member's information"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Staff updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data or staff not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StaffDTO>> updateStaff(
            @Parameter(description = "Staff ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody UpdateStaffDTO dto
    ) {
        try {
            StaffDTO updatedStaff = staffService.updateStaff(id, dto);
            return ResponseEntity.ok(
                    new ApiResponse<>("SUCCESS", "Staff updated successfully", updatedStaff)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("ERROR", e.getMessage(), null)
            );
        }
    }

    @Operation(
        summary = "Partially update staff information",
        description = "Updates specific fields of an existing staff member's information using PATCH."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Staff partially updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data, validation error, or staff not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<StaffDTO>> patchStaff(
            @Parameter(description = "Staff ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody PatchStaffDTO dto
    ) {
        try {
            StaffDTO updatedStaff = staffService.patchStaff(id, dto);
            return ResponseEntity.ok(
                    new ApiResponse<>("SUCCESS", "Staff partially updated successfully", updatedStaff)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("ERROR", e.getMessage(), null)
            );
        }
    }

    @Operation(
        summary = "Update staff working status",
        description = "Updates a staff member's working status (ACTIVE, INACTIVE, ON_LEAVE, etc.)"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Staff status updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data or staff not found")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<MessageDTO>> updateStaffStatus(
            @Parameter(description = "Staff ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody UpdateStatusDTO dto
    ) {
        try {
            MessageDTO message = staffService.updateStaffStatus(id, dto);
            return ResponseEntity.ok(
                    new ApiResponse<>("SUCCESS", "Staff status updated successfully", message)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("ERROR", e.getMessage(), null)
            );
        }
    }
}
