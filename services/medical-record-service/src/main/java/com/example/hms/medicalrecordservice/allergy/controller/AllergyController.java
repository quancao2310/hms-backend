package com.example.hms.medicalrecordservice.allergy.controller;

import com.example.hms.medicalrecordservice.allergy.constant.AllergyErrorMessages;
import com.example.hms.medicalrecordservice.allergy.dto.AllergyCreateRequestDTO;
import com.example.hms.medicalrecordservice.allergy.dto.AllergyResponseDTO;
import com.example.hms.medicalrecordservice.allergy.service.AllergyService;
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
@RequestMapping("/api/v1/patients/{patientId}/allergies")
@RequiredArgsConstructor
@Tag(name = "Allergy Management", description = "API for managing patient's allergies")
public class AllergyController {

    private final AllergyService allergyService;

    @Operation(
            summary = "Get patient's allergies",
            description = "Retrieves paginated allergies for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved allergies"),
            @ApiResponse(responseCode = "400", description = PatientErrorMessages.PATIENT_NOT_FOUND)
    })
    @GetMapping
    public ResponseEntity<Page<AllergyResponseDTO>> getPatientAllergies(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Page number (0-based)", required = true) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", required = true) @RequestParam(defaultValue = "5") int size
    ) {
        Page<AllergyResponseDTO> allergies = allergyService.getAllergiesPageByPatientId(patientId, page, size);
        return ResponseEntity.ok(allergies);
    }

    @Operation(
            summary = "Get patient's allergy by ID",
            description = "Retrieves a specific allergy for a patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved allergy"),
            @ApiResponse(responseCode = "404", description = AllergyErrorMessages.ALLERGY_NOT_FOUND)
    })
    @GetMapping("/{allergyId}")
    public ResponseEntity<AllergyResponseDTO> getAllergyById(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Allergy ID", required = true) @PathVariable UUID allergyId
    ) {
        AllergyResponseDTO allergy = allergyService.getAllergyById(patientId, allergyId);
        return ResponseEntity.ok(allergy);
    }

    @Operation(
            summary = "Add allergy to patient",
            description = "Creates a new allergy record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Allergy created successfully"),
            @ApiResponse(responseCode = "400", description = AllergyErrorMessages.MEDICAL_HISTORY_NOT_FOUND)
    })
    @PostMapping
    public ResponseEntity<AllergyResponseDTO> createAllergy(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Valid @RequestBody AllergyCreateRequestDTO request
    ) {
        AllergyResponseDTO createdAllergy = allergyService.createAllergy(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAllergy);
    }

    @Operation(
            summary = "Update patient's allergy",
            description = "Updates an existing allergy record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Allergy updated successfully"),
            @ApiResponse(responseCode = "400", description = AllergyErrorMessages.ALLERGY_NOT_FOUND)
    })
    @PutMapping("/{allergyId}")
    public ResponseEntity<AllergyResponseDTO> updateAllergy(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Allergy ID", required = true) @PathVariable UUID allergyId,
            @Valid @RequestBody AllergyCreateRequestDTO request
    ) {
        AllergyResponseDTO updatedAllergy = allergyService.updateAllergy(
                patientId, allergyId, request);
        return ResponseEntity.ok(updatedAllergy);
    }

    @Operation(
            summary = "Delete patient's allergy",
            description = "Removes an allergy record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Allergy deleted successfully"),
            @ApiResponse(responseCode = "400", description = AllergyErrorMessages.ALLERGY_NOT_FOUND)
    })
    @DeleteMapping("/{allergyId}")
    public ResponseEntity<Void> deleteAllergy(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Allergy ID", required = true) @PathVariable UUID allergyId
    ) {
        allergyService.deleteAllergy(patientId, allergyId);
        return ResponseEntity.noContent().build();
    }
}