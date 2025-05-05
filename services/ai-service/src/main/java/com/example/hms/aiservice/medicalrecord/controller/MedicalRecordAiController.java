package com.example.hms.aiservice.medicalrecord.controller;

import com.example.hms.aiservice.medicalrecord.dto.DiagnosesInfoDTO;
import com.example.hms.aiservice.medicalrecord.dto.TreatmentSuggestionDTO;
import com.example.hms.aiservice.medicalrecord.service.MedicalRecordAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai/medical-record")
@RequiredArgsConstructor
@Tag(name = "Medical Record AI", description = "API for AI-powered medical record assistance")
public class MedicalRecordAiController {

    private final MedicalRecordAiService medicalRecordAiService;

    @Operation(
            summary = "Get treatment suggestions",
            description = "Generates AI-powered treatment suggestions based on symptoms and diagnoses",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = DiagnosesInfoDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Flu symptoms example",
                                            summary = "Request for flu treatment suggestions",
                                            value = "{ \"symptoms\": \"Fever 101°F, body aches, fatigue, sore throat, dry cough\", \"diagnoses\": \"Influenza A\" }"
                                    ),
                                    @ExampleObject(
                                            name = "Hypertension example",
                                            summary = "Request for hypertension treatment suggestions",
                                            value = "{ \"symptoms\": \"Headache, dizziness, shortness of breath\", \"diagnoses\": \"Essential hypertension with readings 160/95\" }"
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully generated treatment suggestions",
                    content = @Content(
                            schema = @Schema(implementation = TreatmentSuggestionDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Flu treatment suggestion",
                                            summary = "Treatment suggestions for influenza",
                                            value = "{ \"treatments\": \"Rest, increased fluid intake, antipyretics (acetaminophen), antiviral medication if within 48 hours of symptom onset\", \"notes\": \"Monitor temperature, stay hydrated. Seek medical attention if symptoms worsen or persist beyond 7 days.\" }"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Error generating AI suggestions")
    })
    @PostMapping("/treatment-suggestion")
    public ResponseEntity<TreatmentSuggestionDTO> suggestTreatment(
            @RequestBody DiagnosesInfoDTO diagnosesInfoDTO
    ) {
        TreatmentSuggestionDTO treatmentSuggestionDTO = medicalRecordAiService.suggestTreatment(diagnosesInfoDTO);
        return ResponseEntity.ok(treatmentSuggestionDTO);
    }
}