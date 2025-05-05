package com.example.hms.medicalrecordservice.pastdisease.controller;

import com.example.hms.medicalrecordservice.common.constant.CommonErrorMessages;
import com.example.hms.medicalrecordservice.pastdisease.constant.PastDiseaseErrorMessages;
import com.example.hms.medicalrecordservice.pastdisease.dto.PastDiseaseCreateRequestDTO;
import com.example.hms.medicalrecordservice.pastdisease.dto.PastDiseaseResponseDTO;
import com.example.hms.medicalrecordservice.pastdisease.service.PastDiseaseService;
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
@RequestMapping("/api/v1/patients/{patientId}/past-diseases")
@RequiredArgsConstructor
@Tag(name = "Past Disease Management", description = "API for managing patient's past diseases")
public class PastDiseaseController {

    private final PastDiseaseService pastDiseaseService;

    @Operation(
            summary = "Get patient's past diseases",
            description = "Retrieves paginated past diseases for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved past diseases"),
            @ApiResponse(responseCode = "400", description = PatientErrorMessages.PATIENT_NOT_FOUND_WITH_ID)
    })
    @GetMapping
    public ResponseEntity<Page<PastDiseaseResponseDTO>> getPatientPastDiseases(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Page number (0-based)", required = true) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", required = true) @RequestParam(defaultValue = "5") int size
    ) {
        Page<PastDiseaseResponseDTO> pastDiseases = pastDiseaseService.getPastDiseasesPageByPatientId(patientId, page, size);
        return ResponseEntity.ok(pastDiseases);
    }

    @Operation(
            summary = "Get patient's past disease by ID",
            description = "Retrieves a specific past disease for a patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved past disease"),
            @ApiResponse(responseCode = "400", description = PastDiseaseErrorMessages.PAST_DISEASE_NOT_FOUND)
    })
    @GetMapping("/{pastDiseaseId}")
    public ResponseEntity<PastDiseaseResponseDTO> getPastDiseaseById(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Past Disease ID", required = true) @PathVariable UUID pastDiseaseId
    ) {
        PastDiseaseResponseDTO pastDisease = pastDiseaseService.getPastDiseaseById(patientId, pastDiseaseId);
        return ResponseEntity.ok(pastDisease);
    }

    @Operation(
            summary = "Add past disease to patient",
            description = "Creates a new past disease record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Past disease created successfully"),
            @ApiResponse(responseCode = "400", description = CommonErrorMessages.MEDICAL_HISTORY_NOT_FOUND)
    })
    @PostMapping
    public ResponseEntity<PastDiseaseResponseDTO> createPastDisease(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Valid @RequestBody PastDiseaseCreateRequestDTO request
    ) {
        PastDiseaseResponseDTO createdDisease = pastDiseaseService.createPastDisease(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDisease);
    }

    @Operation(
            summary = "Update patient's past disease",
            description = "Updates an existing past disease record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Past disease updated successfully"),
            @ApiResponse(responseCode = "400", description = PastDiseaseErrorMessages.PAST_DISEASE_NOT_FOUND)
    })
    @PutMapping("/{pastDiseaseId}")
    public ResponseEntity<PastDiseaseResponseDTO> updatePastDisease(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Past Disease ID", required = true) @PathVariable UUID pastDiseaseId,
            @Valid @RequestBody PastDiseaseCreateRequestDTO request
    ) {
        PastDiseaseResponseDTO updatedDisease = pastDiseaseService.updatePastDisease(
                patientId, pastDiseaseId, request);
        return ResponseEntity.ok(updatedDisease);
    }

    @Operation(
            summary = "Delete patient's past disease",
            description = "Removes a past disease record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Past disease deleted successfully"),
            @ApiResponse(responseCode = "400", description = PastDiseaseErrorMessages.PAST_DISEASE_NOT_FOUND)
    })
    @DeleteMapping("/{pastDiseaseId}")
    public ResponseEntity<Void> deletePastDisease(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Past Disease ID", required = true) @PathVariable UUID pastDiseaseId
    ) {
        pastDiseaseService.deletePastDisease(patientId, pastDiseaseId);
        return ResponseEntity.noContent().build();
    }
}