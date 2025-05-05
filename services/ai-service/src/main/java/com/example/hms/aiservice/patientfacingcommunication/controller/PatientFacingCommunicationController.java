package com.example.hms.aiservice.patientfacingcommunication.controller;

import com.example.hms.aiservice.patientfacingcommunication.service.PatientFacingCommunicationAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai/patient-communication")
@RequiredArgsConstructor
@Tag(name = "Patient Communication AI", description = "API for AI-powered medical chatbot for patients")
public class PatientFacingCommunicationController {

    private final PatientFacingCommunicationAiService patientFacingCommunicationAiService;

    @Operation(
            summary = "Chat with medical assistant",
            description = "Interact with an AI medical assistant that provides healthcare information, recommendations, and emotional support",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "General health question",
                                            summary = "Question about health habits",
                                            value = "I've been feeling tired lately. What are some healthy habits I could adopt?"
                                    ),
                                    @ExampleObject(
                                            name = "Mental health support",
                                            summary = "Request for anxiety coping strategies",
                                            value = "I've been feeling very anxious lately. Do you have any tips that might help?"
                                    ),
                                    @ExampleObject(
                                            name = "Medical term explanation",
                                            summary = "Question about medical terminology",
                                            value = "What does hypertension mean? Is it dangerous?"
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully processed patient message",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = {
                                    @ExampleObject(
                                            name = "Response to health habits question",
                                            summary = "Advice on addressing fatigue",
                                            value = "I understand feeling tired can be frustrating. Some evidence-based habits that might help include: maintaining a regular sleep schedule (7-9 hours nightly), staying hydrated, engaging in regular physical activity, eating a balanced diet rich in vegetables and whole grains, and managing stress through relaxation techniques. If your fatigue persists for more than two weeks or is severe, I'd recommend speaking with a healthcare provider to rule out medical causes."
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input or patient ID"),
            @ApiResponse(responseCode = "500", description = "Error processing request")
    })
    @PostMapping("/chat/{patientId}")
    public ResponseEntity<String> chatWithAssistant(
            @Parameter(description = "Patient's unique identifier", example = "123456", required = true)
            @PathVariable Long patientId,

            @Parameter(description = "Patient's message to the AI assistant")
            @RequestBody String message
    ) {
        String response = patientFacingCommunicationAiService.assistPatient(patientId, message);
        return ResponseEntity.ok(response);
    }
}