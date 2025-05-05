package com.example.hms.medicalrecordservice.medicalrecord.controller;

import com.example.hms.medicalrecordservice.medicalrecord.constant.MedicalRecordErrorMessages;
import com.example.hms.medicalrecordservice.medicalrecord.dto.MedicalRecordMutationRequestDTO;
import com.example.hms.medicalrecordservice.medicalrecord.dto.MedicalRecordResponseDTO;
import com.example.hms.medicalrecordservice.medicalrecord.service.MedicalRecordService;
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
@RequestMapping("/api/v1/patients/{patientId}/medical-records")
@RequiredArgsConstructor
@Tag(name = "Medical Record Management", description = "API for managing patient's medical records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @Operation(
            summary = "Get patient's medical records",
            description = "Retrieves paginated medical records for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved medical records"),
            @ApiResponse(responseCode = "400", description = PatientErrorMessages.PATIENT_NOT_FOUND_WITH_ID)
    })
    @GetMapping
    public ResponseEntity<Page<MedicalRecordResponseDTO>> getPatientMedicalRecords(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "5") int size
    ) {
        Page<MedicalRecordResponseDTO> medicalRecords = medicalRecordService.getMedicalRecordsByPatientId(patientId, page, size);
        return ResponseEntity.ok(medicalRecords);
    }

    @Operation(
            summary = "Get patient's medical record by ID",
            description = "Retrieves a specific medical record for a patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved medical record"),
            @ApiResponse(responseCode = "400", description = MedicalRecordErrorMessages.RECORD_NOT_FOUND)
    })
    @GetMapping("/{recordId}")
    public ResponseEntity<MedicalRecordResponseDTO> getMedicalRecordById(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Medical Record ID", required = true) @PathVariable UUID recordId
    ) {
        MedicalRecordResponseDTO medicalRecord = medicalRecordService.getMedicalRecordById(patientId, recordId);
        return ResponseEntity.ok(medicalRecord);
    }

    @Operation(
            summary = "Create medical record for patient",
            description = "Creates a new medical record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medical record created successfully"),
            @ApiResponse(responseCode = "400", description = PatientErrorMessages.PATIENT_NOT_FOUND_WITH_ID)
    })
    @PostMapping
    public ResponseEntity<MedicalRecordResponseDTO> createMedicalRecord(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Valid @RequestBody MedicalRecordMutationRequestDTO request
    ) {
        MedicalRecordResponseDTO createdRecord = medicalRecordService.createMedicalRecord(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecord);
    }

    @Operation(
            summary = "Update patient's medical record",
            description = "Updates an existing medical record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical record updated successfully"),
            @ApiResponse(responseCode = "400", description = MedicalRecordErrorMessages.RECORD_NOT_FOUND)
    })
    @PutMapping("/{recordId}")
    public ResponseEntity<MedicalRecordResponseDTO> updateMedicalRecord(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Medical Record ID", required = true) @PathVariable UUID recordId,
            @Valid @RequestBody MedicalRecordMutationRequestDTO request
    ) {
        MedicalRecordResponseDTO updatedRecord = medicalRecordService.updateMedicalRecord(patientId, recordId, request);
        return ResponseEntity.ok(updatedRecord);
    }

    @Operation(
            summary = "Delete patient's medical record",
            description = "Deletes a medical record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medical record deleted successfully"),
            @ApiResponse(responseCode = "400", description = MedicalRecordErrorMessages.RECORD_NOT_FOUND)
    })
    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteMedicalRecord(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Medical Record ID", required = true) @PathVariable UUID recordId
    ) {
        medicalRecordService.deleteMedicalRecord(patientId, recordId);
        return ResponseEntity.noContent().build();
    }
}