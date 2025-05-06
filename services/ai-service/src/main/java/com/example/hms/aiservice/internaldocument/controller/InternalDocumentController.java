package com.example.hms.aiservice.internaldocument.controller;

import com.example.hms.aiservice.internaldocument.service.InternalDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ai/documents")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Internal Document AI", description = "API for managing AI-processed internal documents")
public class InternalDocumentController {

    private final InternalDocumentService internalDocumentService;

    @Operation(
            summary = "Upload and process document",
            description = "Upload a document to be processed, embedded, and stored for future retrieval"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Document successfully processed",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Map.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid file type or size")
    @ApiResponse(responseCode = "500", description = "Error processing document")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadDocument(
            @Parameter(description = "Document file to upload", required = true)
            @RequestParam("file") MultipartFile file) {
            UUID documentId = internalDocumentService.processDocument(file);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "documentId", documentId,
                            "message", "Document successfully processed"
                    ));
    }
}