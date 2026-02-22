package com.pixelforge.nexus.repository;

import com.pixelforge.nexus.model.UploadedDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends MongoRepository<UploadedDocument, String> {
    List<UploadedDocument> findByProjectId(String projectId);
    Optional<UploadedDocument> findByFilename(String filename);
}

