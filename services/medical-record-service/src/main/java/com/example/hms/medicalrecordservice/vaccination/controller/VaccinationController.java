package com.example.hms.medicalrecordservice.vaccination.controller;

import com.example.hms.medicalrecordservice.common.constant.CommonErrorMessages;
import com.example.hms.medicalrecordservice.patientinternalinfo.constant.PatientErrorMessages;
import com.example.hms.medicalrecordservice.vaccination.constant.VaccinationErrorMessages;
import com.example.hms.medicalrecordservice.vaccination.dto.VaccinationCreateRequestDTO;
import com.example.hms.medicalrecordservice.vaccination.dto.VaccinationResponseDTO;
import com.example.hms.medicalrecordservice.vaccination.service.VaccinationService;
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
@RequestMapping("/api/v1/patients/{patientId}/vaccinations")
@RequiredArgsConstructor
@Tag(name = "Vaccination Management", description = "API for managing patient's vaccinations")
public class VaccinationController {

    private final VaccinationService vaccinationService;

    @Operation(
            summary = "Get patient's vaccinations",
            description = "Retrieves paginated vaccinations for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved vaccinations"),
            @ApiResponse(responseCode = "400", description = PatientErrorMessages.PATIENT_NOT_FOUND)
    })
    @GetMapping
    public ResponseEntity<Page<VaccinationResponseDTO>> getPatientVaccinations(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Page number (0-based)", required = true) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", required = true) @RequestParam(defaultValue = "5") int size
    ) {
        Page<VaccinationResponseDTO> vaccinations = vaccinationService.getVaccinationsPageByPatientId(patientId, page, size);
        return ResponseEntity.ok(vaccinations);
    }

    @Operation(
            summary = "Get patient's vaccination by ID",
            description = "Retrieves a specific vaccination for a patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved vaccination"),
            @ApiResponse(responseCode = "400", description = VaccinationErrorMessages.VACCINATION_NOT_FOUND)
    })
    @GetMapping("/{vaccinationId}")
    public ResponseEntity<VaccinationResponseDTO> getVaccinationById(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Vaccination ID", required = true) @PathVariable UUID vaccinationId
    ) {
        VaccinationResponseDTO vaccination = vaccinationService.getVaccinationById(patientId, vaccinationId);
        return ResponseEntity.ok(vaccination);
    }

    @Operation(
            summary = "Add vaccination to patient",
            description = "Creates a new vaccination record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vaccination created successfully"),
            @ApiResponse(responseCode = "400", description = CommonErrorMessages.MEDICAL_HISTORY_NOT_FOUND)
    })
    @PostMapping
    public ResponseEntity<VaccinationResponseDTO> createVaccination(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Valid @RequestBody VaccinationCreateRequestDTO request
    ) {
        VaccinationResponseDTO createdVaccination = vaccinationService.createVaccination(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVaccination);
    }

    @Operation(
            summary = "Update patient's vaccination",
            description = "Updates an existing vaccination record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaccination updated successfully"),
            @ApiResponse(responseCode = "400", description = VaccinationErrorMessages.VACCINATION_NOT_FOUND)
    })
    @PutMapping("/{vaccinationId}")
    public ResponseEntity<VaccinationResponseDTO> updateVaccination(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Vaccination ID", required = true) @PathVariable UUID vaccinationId,
            @Valid @RequestBody VaccinationCreateRequestDTO request
    ) {
        VaccinationResponseDTO updatedVaccination = vaccinationService.updateVaccination(
                patientId, vaccinationId, request);
        return ResponseEntity.ok(updatedVaccination);
    }

    @Operation(
            summary = "Delete patient's vaccination",
            description = "Removes a vaccination record for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vaccination deleted successfully"),
            @ApiResponse(responseCode = "400", description = VaccinationErrorMessages.VACCINATION_NOT_FOUND)
    })
    @DeleteMapping("/{vaccinationId}")
    public ResponseEntity<Void> deleteVaccination(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Parameter(description = "Vaccination ID", required = true) @PathVariable UUID vaccinationId
    ) {
        vaccinationService.deleteVaccination(patientId, vaccinationId);
        return ResponseEntity.noContent().build();
    }
}