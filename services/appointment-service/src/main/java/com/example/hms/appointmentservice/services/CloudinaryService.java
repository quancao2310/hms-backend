package com.example.hms.appointmentservice.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudinaryService {
    String uploadFile(MultipartFile file) throws Exception;
    List<String> uploadFiles(List<MultipartFile> files) throws Exception;
}
