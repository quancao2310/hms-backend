package com.example.hms.aiservice.internaldocument.service.impl;

import com.example.hms.aiservice.internaldocument.service.InternalDocumentService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.IngestionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InternalDocumentServiceImpl implements InternalDocumentService {

    private final EmbeddingStoreIngestor internalDocumentEmbeddingStoreIngestor;

    @Value("${DOCUMENT_MAX_SIZE_MB:10}")
    private Long maxFileSizeMB;

    @Value("${DOCUMENT_ALLOWED_TYPES:application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,text/plain}")
    private List<String> allowedTypes;

    @Value("${DOCUMENT_UPLOAD_DIR:/tmp/uploads}")
    private String uploadDirectory;

    @Override
    public UUID processDocument(MultipartFile file) {
        validateFile(file);
        UUID documentId = UUID.randomUUID();
        try {
            File savedFile = saveFile(file, documentId);
            Document document = parseDocumentFromFile(savedFile);
            storeEmbeddings(document);
            return documentId;
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to process document", e);
        }
    }

    private void validateFile(MultipartFile file) {
        validateFileSize(file);
        validateFileType(file);
    }

    private void validateFileSize(MultipartFile file) {
        if (file.isEmpty() || file.getSize() > maxFileSizeMB * 1024 * 1024) {
            throw new IllegalArgumentException("File is empty or exceeds maximum size of " + maxFileSizeMB + "MB");
        }
    }

    private void validateFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !allowedTypes.contains(contentType)) {
            throw new IllegalArgumentException("File type " + contentType + " not allowed");
        }
    }

    private File saveFile(MultipartFile file, UUID documentId) throws IOException {
        Path uploadDir = Paths.get(uploadDirectory);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        Path filePath = uploadDir.resolve(documentId + fileExtension);
        Files.copy(file.getInputStream(), filePath);
        return filePath.toFile();
    }

    private String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex < 0 ? "" : filename.substring(lastDotIndex);
    }

    private Document parseDocumentFromFile(File file) {
        return FileSystemDocumentLoader.loadDocument(file.getPath(), new ApacheTikaDocumentParser());
    }

    private void storeEmbeddings(Document document) {
        IngestionResult ingestionResult = internalDocumentEmbeddingStoreIngestor.ingest(document);
        log.info("Token usage: {}", ingestionResult.tokenUsage());
    }
}