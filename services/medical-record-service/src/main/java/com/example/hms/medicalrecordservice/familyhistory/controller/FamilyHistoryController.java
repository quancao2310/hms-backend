package com.example.hms.medicalrecordservice.familyhistory.controller;

import com.example.hms.medicalrecordservice.common.constant.CommonErrorMessages;
import com.example.hms.medicalrecordservice.familyhistory.constant.FamilyHistoryErrorMessages;
import com.example.hms.medicalrecordservice.familyhistory.dto.FamilyHistoryCreateRequestDTO;
import com.example.hms.medicalrecordservice.familyhistory.dto.FamilyHistoryResponseDTO;
import com.example.hms.medicalrecordservice.familyhistory.service.FamilyHistoryService;
import com.example.hms.medicalrecordservice.patientinternalinfo.constant.PatientErrorMessages;
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
@RequestMapping("/api/v1/patients/{patientId}/family-histories")
@RequiredArgsConstructor
@Tag(name = "Family History Management", description = "API for managing patient's family histories")
public class FamilyHistoryController {

    private final FamilyHistoryService familyHistoryService;

    @Operation(
            summary = "Get patient's family histories",
            description = "Retrieves paginated family histories for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved family histories"),
            @ApiResponse(responseCode = "400", description = PatientErrorMessages.PATIENT_NOT_FOUND)
    })
    @GetMapping
    public ResponseEntity<Page<FamilyHistoryResponseDTO>> getPatientFamilyHistories(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Page number (0-based)", required = true) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", required = true) @RequestParam(defaultValue = "5") int size
    ) {
        Page<FamilyHistoryResponseDTO> familyHistories = familyHistoryService.getFamilyHistoriesPageByPatientId(patientId, page, size);
        return ResponseEntity.ok(familyHistories);
    }

    @Operation(
            summary = "Get patient's family history by ID",
            description = "Retrieves a specific family history for a patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved family history"),
            @ApiResponse(responseCode = "400", description = FamilyHistoryErrorMessages.FAMILY_HISTORY_NOT_FOUND)
    })
    @GetMapping("/{familyHistoryId}")
    public ResponseEntity<FamilyHistoryResponseDTO> getFamilyHistoryById(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Family History ID", required = true) @PathVariable UUID familyHistoryId
    ) {
        FamilyHistoryResponseDTO familyHistory = familyHistoryService.getFamilyHistoryById(patientId, familyHistoryId);
        return ResponseEntity.ok(familyHistory);
    }

    @Operation(
            summary = "Add family history to patient",
            description = "Creates a new family history record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Family history created successfully"),
            @ApiResponse(responseCode = "400", description = CommonErrorMessages.MEDICAL_HISTORY_NOT_FOUND)
    })
    @PostMapping
    public ResponseEntity<FamilyHistoryResponseDTO> createFamilyHistory(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Valid @RequestBody FamilyHistoryCreateRequestDTO request
    ) {
        FamilyHistoryResponseDTO createdHistory = familyHistoryService.createFamilyHistory(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHistory);
    }

    @Operation(
            summary = "Update patient's family history",
            description = "Updates an existing family history record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Family history updated successfully"),
            @ApiResponse(responseCode = "400", description = FamilyHistoryErrorMessages.FAMILY_HISTORY_NOT_FOUND)
    })
    @PutMapping("/{familyHistoryId}")
    public ResponseEntity<FamilyHistoryResponseDTO> updateFamilyHistory(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Family History ID", required = true) @PathVariable UUID familyHistoryId,
            @Valid @RequestBody FamilyHistoryCreateRequestDTO request
    ) {
        FamilyHistoryResponseDTO updatedHistory = familyHistoryService.updateFamilyHistory(
                patientId, familyHistoryId, request);
        return ResponseEntity.ok(updatedHistory);
    }

    @Operation(
            summary = "Delete patient's family history",
            description = "Removes a family history record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Family history deleted successfully"),
            @ApiResponse(responseCode = "400", description = FamilyHistoryErrorMessages.FAMILY_HISTORY_NOT_FOUND)
    })
    @DeleteMapping("/{familyHistoryId}")
    public ResponseEntity<Void> deleteFamilyHistory(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Family History ID", required = true) @PathVariable UUID familyHistoryId
    ) {
        familyHistoryService.deleteFamilyHistory(patientId, familyHistoryId);
        return ResponseEntity.noContent().build();
    }
}