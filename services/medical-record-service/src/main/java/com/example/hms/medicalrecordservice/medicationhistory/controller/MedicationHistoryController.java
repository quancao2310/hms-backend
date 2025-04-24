package com.example.hms.medicalrecordservice.medicationhistory.controller;

import com.example.hms.medicalrecordservice.medicationhistory.constant.MedicationHistoryErrorMessages;
import com.example.hms.medicalrecordservice.medicationhistory.dto.MedicationHistoryCreateRequestDTO;
import com.example.hms.medicalrecordservice.medicationhistory.dto.MedicationHistoryResponseDTO;
import com.example.hms.medicalrecordservice.medicationhistory.service.MedicationHistoryService;
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
@RequestMapping("/api/v1/patients/{patientId}/medication-histories")
@RequiredArgsConstructor
@Tag(name = "Medication History Management", description = "API for managing patient's medication histories")
public class MedicationHistoryController {

    private final MedicationHistoryService medicationHistoryService;

    @Operation(
            summary = "Get patient's medication histories",
            description = "Retrieves paginated medication histories for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved medication histories"),
            @ApiResponse(responseCode = "400", description = PatientErrorMessages.PATIENT_NOT_FOUND)
    })
    @GetMapping
    public ResponseEntity<Page<MedicationHistoryResponseDTO>> getPatientMedicationHistories(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Page number (0-based)", required = true) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", required = true) @RequestParam(defaultValue = "5") int size
    ) {
        Page<MedicationHistoryResponseDTO> medicationHistories = medicationHistoryService.getMedicationHistoriesPageByPatientId(patientId, page, size);
        return ResponseEntity.ok(medicationHistories);
    }

    @Operation(
            summary = "Get patient's medication history by ID",
            description = "Retrieves a specific medication history for a patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved medication history"),
            @ApiResponse(responseCode = "404", description = MedicationHistoryErrorMessages.MEDICATION_HISTORY_NOT_FOUND)
    })
    @GetMapping("/{medicationHistoryId}")
    public ResponseEntity<MedicationHistoryResponseDTO> getMedicationHistoryById(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Medication History ID", required = true) @PathVariable UUID medicationHistoryId
    ) {
        MedicationHistoryResponseDTO medicationHistory = medicationHistoryService.getMedicationHistoryById(patientId, medicationHistoryId);
        return ResponseEntity.ok(medicationHistory);
    }

    @Operation(
            summary = "Add medication history to patient",
            description = "Creates a new medication history record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medication history created successfully"),
            @ApiResponse(responseCode = "400", description = MedicationHistoryErrorMessages.MEDICAL_HISTORY_NOT_FOUND)
    })
    @PostMapping
    public ResponseEntity<MedicationHistoryResponseDTO> createMedicationHistory(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Valid @RequestBody MedicationHistoryCreateRequestDTO request
    ) {
        MedicationHistoryResponseDTO createdMedication = medicationHistoryService.createMedicationHistory(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMedication);
    }

    @Operation(
            summary = "Update patient's medication history",
            description = "Updates an existing medication history record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medication history updated successfully"),
            @ApiResponse(responseCode = "400", description = MedicationHistoryErrorMessages.MEDICATION_HISTORY_NOT_FOUND)
    })
    @PutMapping("/{medicationHistoryId}")
    public ResponseEntity<MedicationHistoryResponseDTO> updateMedicationHistory(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Medication History ID", required = true) @PathVariable UUID medicationHistoryId,
            @Valid @RequestBody MedicationHistoryCreateRequestDTO request
    ) {
        MedicationHistoryResponseDTO updatedMedication = medicationHistoryService.updateMedicationHistory(
                patientId, medicationHistoryId, request);
        return ResponseEntity.ok(updatedMedication);
    }

    @Operation(
            summary = "Delete patient's medication history",
            description = "Removes a medication history record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medication history deleted successfully"),
            @ApiResponse(responseCode = "400", description = MedicationHistoryErrorMessages.MEDICATION_HISTORY_NOT_FOUND)
    })
    @DeleteMapping("/{medicationHistoryId}")
    public ResponseEntity<Void> deleteMedicationHistory(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Medication History ID", required = true) @PathVariable UUID medicationHistoryId
    ) {
        medicationHistoryService.deleteMedicationHistory(patientId, medicationHistoryId);
        return ResponseEntity.noContent().build();
    }
}