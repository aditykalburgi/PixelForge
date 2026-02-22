package com.pixelforge.nexus.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "projects")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private String id;

    private String name;

    private String description;

    private LocalDate deadline;

    @Builder.Default
    private ProjectStatus status = ProjectStatus.ACTIVE;

    private String createdById;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}

