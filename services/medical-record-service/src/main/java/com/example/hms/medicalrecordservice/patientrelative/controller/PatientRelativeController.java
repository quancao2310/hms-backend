package com.example.hms.medicalrecordservice.patientrelative.controller;

import com.example.hms.medicalrecordservice.patientinternalinfo.constant.PatientErrorMessages;
import com.example.hms.medicalrecordservice.patientrelative.constant.PatientRelativeErrorMessages;
import com.example.hms.medicalrecordservice.patientrelative.dto.PatientRelativeCreateRequestDTO;
import com.example.hms.medicalrecordservice.patientrelative.dto.PatientRelativeResponseDTO;
import com.example.hms.medicalrecordservice.patientrelative.service.PatientRelativeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients/{patientId}/relatives")
@RequiredArgsConstructor
@Tag(name = "Patient Relative Management", description = "API for managing patient's relatives")
public class PatientRelativeController {

    private final PatientRelativeService patientRelativeService;

    @Operation(
            summary = "Get patient's relatives",
            description = "Retrieves paginated relatives for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved relatives"),
            @ApiResponse(responseCode = "400", description = PatientErrorMessages.PATIENT_NOT_FOUND_WITH_ID)
    })
    @GetMapping
    public ResponseEntity<Page<PatientRelativeResponseDTO>> getPatientRelatives(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Page number (0-based)", required = true) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", required = true) @RequestParam(defaultValue = "5") int size
    ) {
        Page<PatientRelativeResponseDTO> relatives = patientRelativeService.getPatientRelativesPageByPatientId(patientId, page, size);
        return ResponseEntity.ok(relatives);
    }

    @Operation(
            summary = "Get patient's relative by ID",
            description = "Retrieves a specific relative for a patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved relative"),
            @ApiResponse(responseCode = "400", description = PatientRelativeErrorMessages.PATIENT_RELATIVE_NOT_FOUND)
    })
    @GetMapping("/{relativeId}")
    public ResponseEntity<PatientRelativeResponseDTO> getPatientRelativeById(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Relative ID", required = true) @PathVariable UUID relativeId
    ) {
        PatientRelativeResponseDTO relative = patientRelativeService.getPatientRelativeById(patientId, relativeId);
        return ResponseEntity.ok(relative);
    }

    @Operation(
            summary = "Add relative to patient",
            description = "Creates a new relative record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Relative created successfully"),
            @ApiResponse(responseCode = "400", description = PatientErrorMessages.PATIENT_NOT_FOUND_WITH_ID)
    })
    @PostMapping
    public ResponseEntity<PatientRelativeResponseDTO> createPatientRelative(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Valid @RequestBody PatientRelativeCreateRequestDTO request
    ) {
        PatientRelativeResponseDTO createdRelative = patientRelativeService.createPatientRelative(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRelative);
    }

    @Operation(
            summary = "Update patient's relative",
            description = "Updates an existing relative record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relative updated successfully"),
            @ApiResponse(responseCode = "400", description = PatientRelativeErrorMessages.PATIENT_RELATIVE_NOT_FOUND)
    })
    @PutMapping("/{relativeId}")
    public ResponseEntity<PatientRelativeResponseDTO> updatePatientRelative(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Relative ID", required = true) @PathVariable UUID relativeId,
            @Valid @RequestBody PatientRelativeCreateRequestDTO request
    ) {
        PatientRelativeResponseDTO updatedRelative = patientRelativeService.updatePatientRelative(
                patientId, relativeId, request);
        return ResponseEntity.ok(updatedRelative);
    }

    @Operation(
            summary = "Delete patient's relative",
            description = "Removes a relative record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Relative deleted successfully"),
            @ApiResponse(responseCode = "400", description = PatientRelativeErrorMessages.PATIENT_RELATIVE_NOT_FOUND)
    })
    @DeleteMapping("/{relativeId}")
    public ResponseEntity<Void> deletePatientRelative(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Relative ID", required = true) @PathVariable UUID relativeId
    ) {
        patientRelativeService.deletePatientRelative(patientId, relativeId);
        return ResponseEntity.noContent().build();
    }
}