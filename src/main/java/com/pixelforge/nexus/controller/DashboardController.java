package com.pixelforge.nexus.controller;

import com.pixelforge.nexus.model.Project;
import com.pixelforge.nexus.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String dashboard(Authentication authentication, Model model) {
        String userId = authentication.getName();
        String userRole = extractRole(authentication.getAuthorities());

        List<Project> projects = projectService.getProjectsForUser(userId, userRole);

        model.addAttribute("projects", projects);
        model.addAttribute("projectCount", projects.size());
        model.addAttribute("userRole", userRole);

        return "dashboard";
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

