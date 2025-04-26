package com.example.hms.appointmentservice.services;

import com.example.hms.appointmentservice.dtos.UrlDTO;
import com.example.hms.appointmentservice.dtos.UrlsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudinaryService {
    UrlDTO uploadFile(MultipartFile file) throws Exception;
    UrlsDTO uploadFiles(List<MultipartFile> files) throws Exception;
}
