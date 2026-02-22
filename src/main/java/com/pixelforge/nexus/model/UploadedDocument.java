package com.pixelforge.nexus.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "uploaded_documents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadedDocument {
    @Id
    private String id;

    private String projectId;

    private String filename;

    private String originalName;

    private String uploadedById;

    @Builder.Default
    private LocalDateTime uploadedAt = LocalDateTime.now();
}

