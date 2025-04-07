package com.example.hms.appointmentservice.controllers;

import com.example.hms.appointmentservice.services.CloudinaryService;
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
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(cloudinaryService.uploadFile(file));
    }

    @PostMapping("/upload/many")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws Exception {
        return ResponseEntity.ok(cloudinaryService.uploadFiles(files));
    }
}
