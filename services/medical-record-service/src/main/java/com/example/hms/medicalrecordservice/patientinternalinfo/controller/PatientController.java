package com.example.hms.medicalrecordservice.patientinternalinfo.controller;

import com.example.hms.medicalrecordservice.common.constant.CommonErrorMessages;
import com.example.hms.medicalrecordservice.medicalinfo.service.MedicalInfoService;
import com.example.hms.medicalrecordservice.patientinternalinfo.constant.PatientErrorMessages;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientCreateRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientMutationRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientResponseDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientWithLatestRecordDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patient Management", description = "API for managing patients")
public class PatientController {

    private final PatientService patientService;
    private final MedicalInfoService medicalInfoService;

    @Operation(
            summary = "Create a new patient",
            description = "Creates a new patient with the provided details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = CommonErrorMessages.INVALID_INPUT),
            @ApiResponse(responseCode = "409", description = PatientErrorMessages.PATIENT_SSN_ALREADY_EXISTS)
    })
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientCreateRequestDTO request) {
        PatientResponseDTO createdPatient = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @Operation(
            summary = "Get all patients with latest medical records (if have any)",
            description = "Retrieves a paginated list of patients with their latest medical records if available"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved patients with latest records")
    })
    @GetMapping
    public ResponseEntity<Page<PatientWithLatestRecordDTO>> getPatientsWithLatestRecord(
            @Parameter(description = "Page number (zero-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Size of each page") @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(patientService.getPatientsWithLatestMedicalRecord(page, size));
    }

    @Operation(
            summary = "Get patient by ID",
            description = "Retrieves a patient by their unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved patient"),
            @ApiResponse(responseCode = "404", description = PatientErrorMessages.PATIENT_NOT_FOUND_WITH_ID)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID id
    ) {
        PatientResponseDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @Operation(
            summary = "Get patient by SSN",
            description = "Retrieves a patient by their Social Security Number (SSN)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved patient"),
            @ApiResponse(responseCode = "404", description = PatientErrorMessages.PATIENT_NOT_FOUND_WITH_SSN)
    })
    @GetMapping("/ssn/{ssn}")
    public ResponseEntity<PatientResponseDTO> getPatientBySsn(
            @Parameter(description = "Patient SSN", required = true) @PathVariable String ssn
    ) {
        PatientResponseDTO patient = patientService.getPatientBySsn(ssn);
        return ResponseEntity.ok(patient);
    }

    @Operation(
            summary = "Update patient information",
            description = "Updates an existing patient's information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = PatientErrorMessages.PATIENT_NOT_FOUND_WITH_ID)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody PatientMutationRequestDTO request
    ) {
        PatientResponseDTO updatedPatient = patientService.updatePatient(id, request);
        return ResponseEntity.ok(updatedPatient);
    }
}