package com.pixelforge.nexus.controller;

import com.pixelforge.nexus.model.Project;
import com.pixelforge.nexus.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> dashboard(Authentication authentication) {
        String userId = authentication.getName();
        String userRole = extractRole(authentication.getAuthorities());

        List<Project> projects = projectService.getProjectsForUser(userId, userRole);

        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("projects", projects);
        response.put("projectCount", projects.size());
        response.put("userRole", userRole);

        return ResponseEntity.ok(response);
    }

    private String extractRole(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .filter(auth -> auth.startsWith("ROLE_"))
            .map(auth -> auth.substring(5))
            .findFirst()
            .orElse("DEVELOPER");
    }
}

