package com.pixelforge.nexus.repository;

import com.pixelforge.nexus.model.Project;
import com.pixelforge.nexus.model.ProjectStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByCreatedById(String createdById);
}

