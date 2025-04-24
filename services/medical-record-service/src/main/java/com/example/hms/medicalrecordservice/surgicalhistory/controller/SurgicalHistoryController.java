package com.example.hms.medicalrecordservice.surgicalhistory.controller;

import com.example.hms.medicalrecordservice.common.constant.CommonErrorMessages;
import com.example.hms.medicalrecordservice.patientinternalinfo.constant.PatientErrorMessages;
import com.example.hms.medicalrecordservice.surgicalhistory.constant.SurgicalHistoryErrorMessages;
import com.example.hms.medicalrecordservice.surgicalhistory.dto.SurgicalHistoryCreateRequestDTO;
import com.example.hms.medicalrecordservice.surgicalhistory.dto.SurgicalHistoryResponseDTO;
import com.example.hms.medicalrecordservice.surgicalhistory.service.SurgicalHistoryService;
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
@RequestMapping("/api/v1/patients/{patientId}/surgical-history")
@RequiredArgsConstructor
@Tag(name = "Surgical History Management", description = "API for managing patient's surgical history")
public class SurgicalHistoryController {

    private final SurgicalHistoryService surgicalHistoryService;

    @Operation(
            summary = "Get patient's surgical history",
            description = "Retrieves paginated surgical history for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved surgical history"),
            @ApiResponse(responseCode = "400", description = PatientErrorMessages.PATIENT_NOT_FOUND)
    })
    @GetMapping
    public ResponseEntity<Page<SurgicalHistoryResponseDTO>> getPatientSurgicalHistory(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Page number (0-based)", required = true) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", required = true) @RequestParam(defaultValue = "5") int size
    ) {
        Page<SurgicalHistoryResponseDTO> surgicalHistories = surgicalHistoryService.getSurgicalHistoriesPageByPatientId(patientId, page, size);
        return ResponseEntity.ok(surgicalHistories);
    }

    @Operation(
            summary = "Get patient's surgical history by ID",
            description = "Retrieves a specific surgical history record for a patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved surgical history"),
            @ApiResponse(responseCode = "404", description = SurgicalHistoryErrorMessages.SURGICAL_HISTORY_NOT_FOUND)
    })
    @GetMapping("/{surgicalHistoryId}")
    public ResponseEntity<SurgicalHistoryResponseDTO> getSurgicalHistoryById(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Surgical History ID", required = true) @PathVariable UUID surgicalHistoryId
    ) {
        SurgicalHistoryResponseDTO surgicalHistory = surgicalHistoryService.getSurgicalHistoryById(patientId, surgicalHistoryId);
        return ResponseEntity.ok(surgicalHistory);
    }

    @Operation(
            summary = "Add surgical history to patient",
            description = "Creates a new surgical history record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Surgical history created successfully"),
            @ApiResponse(responseCode = "400", description = SurgicalHistoryErrorMessages.MEDICAL_HISTORY_NOT_FOUND)
    })
    @PostMapping
    public ResponseEntity<SurgicalHistoryResponseDTO> createSurgicalHistory(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Valid @RequestBody SurgicalHistoryCreateRequestDTO request
    ) {
        SurgicalHistoryResponseDTO createdHistory = surgicalHistoryService.createSurgicalHistory(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHistory);
    }

    @Operation(
            summary = "Update patient's surgical history",
            description = "Updates an existing surgical history record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Surgical history updated successfully"),
            @ApiResponse(responseCode = "400", description = SurgicalHistoryErrorMessages.SURGICAL_HISTORY_NOT_FOUND)
    })
    @PutMapping("/{surgicalHistoryId}")
    public ResponseEntity<SurgicalHistoryResponseDTO> updateSurgicalHistory(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Surgical History ID", required = true) @PathVariable UUID surgicalHistoryId,
            @Valid @RequestBody SurgicalHistoryCreateRequestDTO request
    ) {
        SurgicalHistoryResponseDTO updatedHistory = surgicalHistoryService.updateSurgicalHistory(
                patientId, surgicalHistoryId, request);
        return ResponseEntity.ok(updatedHistory);
    }

    @Operation(
            summary = "Delete patient's surgical history",
            description = "Removes a surgical history record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Surgical history deleted successfully"),
            @ApiResponse(responseCode = "400", description = SurgicalHistoryErrorMessages.SURGICAL_HISTORY_NOT_FOUND)
    })
    @DeleteMapping("/{surgicalHistoryId}")
    public ResponseEntity<Void> deleteSurgicalHistory(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Surgical History ID", required = true) @PathVariable UUID surgicalHistoryId
    ) {
        surgicalHistoryService.deleteSurgicalHistory(patientId, surgicalHistoryId);
        return ResponseEntity.noContent().build();
    }
}