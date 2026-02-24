package com.pixelforge.nexus.controller;

import com.pixelforge.nexus.model.ProjectMember;
import com.pixelforge.nexus.model.UploadedDocument;
import com.pixelforge.nexus.model.User;
import com.pixelforge.nexus.repository.UserRepository;
import com.pixelforge.nexus.service.DocumentService;
import com.pixelforge.nexus.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/uploads")
public class FileController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{filename}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String filename, Authentication authentication) {
        try {
            Optional<UploadedDocument> documentOpt = documentService.findByFilename(filename);
            if (!documentOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            UploadedDocument document = documentOpt.get();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username).orElse(null);

            if (user == null) {
                return ResponseEntity.status(403).build();
            }

            boolean hasAccess = hasAccessToProject(user, document.getProjectId());
            if (!hasAccess) {
                return ResponseEntity.status(403).build();
            }

            byte[] fileContent = documentService.downloadDocument(filename);
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(fileContent));

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getOriginalName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(fileContent.length)
                .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    private boolean hasAccessToProject(User user, String projectId) {
        String userRole = extractRole(user);

        if ("ADMIN".equals(userRole) || "PROJECT_LEAD".equals(userRole)) {
            return true;
        }

        List<ProjectMember> members = projectService.getProjectMembers(projectId);
        return members.stream().anyMatch(member -> member.getUserId().equals(user.getId()));
    }

    private String extractRole(User user) {
        return user.getRole().name();
    }
}

