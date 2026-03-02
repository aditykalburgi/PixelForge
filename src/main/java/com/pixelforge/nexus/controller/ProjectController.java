package com.pixelforge.nexus.controller;

import com.pixelforge.nexus.dto.CreateProjectDTO;
import com.pixelforge.nexus.model.Project;
import com.pixelforge.nexus.model.ProjectMember;
import com.pixelforge.nexus.model.UploadedDocument;
import com.pixelforge.nexus.model.User;
import com.pixelforge.nexus.repository.UserRepository;
import com.pixelforge.nexus.service.DocumentService;
import com.pixelforge.nexus.service.ProjectService;
import com.pixelforge.nexus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Project>> listProjects() {
        List<Project> projects = projectService.findAll();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> projectDetail(@PathVariable String id) {
        try {
            Optional<Project> projectOpt = projectService.getProjectDetail(id);
            if (projectOpt.isPresent()) {
                Project project = projectOpt.get();
                List<ProjectMember> members = projectService.getProjectMembers(id);
                List<UploadedDocument> documents = documentService.getProjectDocuments(id);
                List<User> availableUsers = userRepository.findAll();

                java.util.Map<String, Object> response = new java.util.HashMap<>();
                response.put("project", project);
                response.put("members", members);
                response.put("documents", documents);
                response.put("availableUsers", availableUsers);
                
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body("Project not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@Valid @RequestBody CreateProjectDTO dto, Authentication authentication) {
        try {
            String userId = authentication.getName();
            User user = userRepository.findByUsername(userId).orElse(null);

            if (user != null) {
                projectService.createProject(dto, user.getId());
                return ResponseEntity.ok(java.util.Map.of("message", "Project created successfully"));
            } else {
                return ResponseEntity.status(404).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating project: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeProject(@PathVariable String id) {
        try {
            projectService.completeProject(id);
            return ResponseEntity.ok(java.util.Map.of("message", "Project completed successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error completing project: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/assign")
    public ResponseEntity<?> assignMember(@PathVariable String id, @RequestParam String userId, Authentication authentication) {
        try {
            String assignedById = authentication.getName();
            User assignedByUser = userRepository.findByUsername(assignedById).orElse(null);

            if (assignedByUser != null) {
                boolean success = projectService.assignMember(id, userId, assignedByUser.getId());
                if (success) {
                    return ResponseEntity.ok(java.util.Map.of("message", "Member assigned successfully"));
                } else {
                    return ResponseEntity.status(400).body("Member is already assigned to this project");
                }
            } else {
                return ResponseEntity.status(404).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error assigning member: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<?> uploadDocument(@PathVariable String id, @RequestParam MultipartFile file, Authentication authentication) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(400).body("Please select a file to upload");
            }

            String uploadedById = authentication.getName();
            User user = userRepository.findByUsername(uploadedById).orElse(null);

            if (user != null) {
                documentService.uploadDocument(id, file, user.getId());
                return ResponseEntity.ok(java.util.Map.of("message", "Document uploaded successfully"));
            } else {
                return ResponseEntity.status(404).body("User not found");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
        }
    }
}

