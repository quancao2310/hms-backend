package com.example.hms.appointmentservice.controllers;

import com.example.hms.appointmentservice.dtos.UrlDTO;
import com.example.hms.appointmentservice.dtos.UrlsDTO;
import com.example.hms.appointmentservice.services.CloudinaryService;
import com.example.hms.models.internal.appointment.DoctorTimeSlot;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class CloudinaryController {
    private final CloudinaryService cloudinaryService;

    @PostMapping(value = "/upload/single", consumes = "multipart/form-data")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UrlDTO.class)
                    )
            )
    })
    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(cloudinaryService.uploadFile(file));
    }

    @PostMapping(value = "/upload/many", consumes = "multipart/form-data")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UrlsDTO.class))
            )
    })
    public ResponseEntity<?> uploadFiles(@RequestPart("files") List<MultipartFile> files) throws Exception {
        return ResponseEntity.ok(cloudinaryService.uploadFiles(files));
    }
}
