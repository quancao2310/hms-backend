package com.example.hms.appointmentservice.controllers;

import com.example.hms.appointmentservice.services.CloudinaryService;
import com.example.hms.models.internal.appointment.DoctorTimeSlot;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class CloudinaryController {
    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload/single")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            )
    })
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(cloudinaryService.uploadFile(file));
    }

    @PostMapping("/upload/many")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = String.class))
                    )
            )
    })
    public ResponseEntity<?> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws Exception {
        return ResponseEntity.ok(cloudinaryService.uploadFiles(files));
    }
}
