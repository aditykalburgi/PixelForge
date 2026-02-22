package com.pixelforge.nexus.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "project_members")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember {
    @Id
    private String id;

    private String projectId;

    private String userId;

    private String assignedById;

    @Builder.Default
    private LocalDateTime assignedAt = LocalDateTime.now();
}

