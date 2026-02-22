package com.pixelforge.nexus.service;

import com.pixelforge.nexus.model.UploadedDocument;
import com.pixelforge.nexus.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    private static final String UPLOAD_DIR = "./uploads";
    private static final String[] ALLOWED_EXTENSIONS = {"pdf", "doc", "docx", "txt", "png", "jpg"};

    public DocumentService() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    public UploadedDocument uploadDocument(String projectId, MultipartFile file, String uploadedById) throws IOException {
        String originalName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalName);

        if (!isAllowedExtension(fileExtension)) {
            throw new IllegalArgumentException("File type not allowed. Allowed types: pdf, doc, docx, txt, png, jpg");
        }

        String filename = UUID.randomUUID().toString() + "." + fileExtension;
        Path filePath = Paths.get(UPLOAD_DIR, filename);

        Files.write(filePath, file.getBytes());

        UploadedDocument document = UploadedDocument.builder()
            .projectId(projectId)
            .filename(filename)
            .originalName(originalName)
            .uploadedById(uploadedById)
            .build();

        return documentRepository.save(document);
    }

    public List<UploadedDocument> getProjectDocuments(String projectId) {
        return documentRepository.findByProjectId(projectId);
    }

    public Optional<UploadedDocument> findByFilename(String filename) {
        return documentRepository.findByFilename(filename);
    }

    public byte[] downloadDocument(String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR, filename);
        return Files.readAllBytes(filePath);
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    private boolean isAllowedExtension(String extension) {
        for (String allowed : ALLOWED_EXTENSIONS) {
            if (allowed.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}

