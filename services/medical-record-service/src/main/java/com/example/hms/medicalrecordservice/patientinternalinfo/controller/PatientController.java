package com.example.hms.medicalrecordservice.patientinternalinfo.controller;

import com.example.hms.medicalrecordservice.patientinternalinfo.constant.ErrorMessages;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientCreateRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientResponseDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patient Management", description = "API for managing patients")
public class PatientController {

    private final PatientService patientService;

    @Operation(
            summary = "Create a new patient",
            description = "Creates a new patient with the provided details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = ErrorMessages.INVALID_INPUT),
            @ApiResponse(responseCode = "409", description = ErrorMessages.PATIENT_SSN_ALREADY_EXISTS)
    })
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientCreateRequestDTO request) {
        PatientResponseDTO createdPatient = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @Operation(
            summary = "Get patient by ID",
            description = "Retrieves a patient by their unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved patient"),
            @ApiResponse(responseCode = "404", description = ErrorMessages.PATIENT_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID id
    ) {
        PatientResponseDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }
}