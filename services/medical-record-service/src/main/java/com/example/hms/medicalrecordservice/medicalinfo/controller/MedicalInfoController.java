package com.example.hms.medicalrecordservice.medicalinfo.controller;

import com.example.hms.medicalrecordservice.medicalinfo.constant.MedicalInfoErrorMessages;
import com.example.hms.medicalrecordservice.medicalinfo.dto.MedicalInfoMutationRequestDTO;
import com.example.hms.medicalrecordservice.medicalinfo.dto.MedicalInfoResponseDTO;
import com.example.hms.medicalrecordservice.medicalinfo.service.MedicalInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients/{patientId}/medical-info")
@RequiredArgsConstructor
@Tag(name = "Medical Information Management", description = "API for managing patient's medical information")
public class MedicalInfoController {

    private final MedicalInfoService medicalInfoService;

    @Operation(
            summary = "Get patient's medical information",
            description = "Retrieves medical information for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved medical info"),
            @ApiResponse(responseCode = "400", description = MedicalInfoErrorMessages.MEDICAL_INFO_NOT_FOUND)
    })
    @GetMapping
    public ResponseEntity<MedicalInfoResponseDTO> getPatientMedicalInfo(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId
    ) {
        MedicalInfoResponseDTO medicalInfo = medicalInfoService.getPatientMedicalInfo(patientId);
        return ResponseEntity.ok(medicalInfo);
    }

    @Operation(
            summary = "Update patient's medical information",
            description = "Updates medical information for a specific patient"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical info updated successfully"),
            @ApiResponse(responseCode = "400", description = MedicalInfoErrorMessages.MEDICAL_INFO_NOT_FOUND)
    })
    @PutMapping
    public ResponseEntity<MedicalInfoResponseDTO> updatePatientMedicalInfo(
            @Parameter(description = "Patient ID", required = true) @PathVariable UUID patientId,
            @Valid @RequestBody MedicalInfoMutationRequestDTO request
    ) {
        MedicalInfoResponseDTO updatedMedicalInfo = medicalInfoService.updatePatientMedicalInfo(patientId, request);
        return ResponseEntity.ok(updatedMedicalInfo);
    }
}