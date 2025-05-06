package com.example.hms.aiservice.internaldocument.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface InternalDocumentService {
    UUID processDocument(MultipartFile file);
}
