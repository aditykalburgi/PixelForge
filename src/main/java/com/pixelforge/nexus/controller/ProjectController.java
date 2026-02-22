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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listProjects(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "projects/list";
    }

    @GetMapping("/{id}")
    public String projectDetail(@PathVariable String id, Model model) {
        try {
            Optional<Project> projectOpt = projectService.getProjectDetail(id);
            if (projectOpt.isPresent()) {
                Project project = projectOpt.get();
                List<ProjectMember> members = projectService.getProjectMembers(id);
                List<UploadedDocument> documents = documentService.getProjectDocuments(id);
                List<User> availableUsers = userRepository.findAll();

                model.addAttribute("project", project);
                model.addAttribute("members", members);
                model.addAttribute("documents", documents);
                model.addAttribute("availableUsers", availableUsers);
                return "projects/detail";
            } else {
                return "redirect:/projects";
            }
        } catch (Exception e) {
            return "redirect:/projects";
        }
    }

    @PostMapping("/create")
    public String createProject(@Valid @ModelAttribute CreateProjectDTO dto, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            String userId = authentication.getName();
            User user = userRepository.findByUsername(userId).orElse(null);

            if (user != null) {
                projectService.createProject(dto, user.getId());
                redirectAttributes.addFlashAttribute("success", "Project created successfully");
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating project: " + e.getMessage());
        }
        return "redirect:/projects";
    }

    @PostMapping("/{id}/complete")
    public String completeProject(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            projectService.completeProject(id);
            redirectAttributes.addFlashAttribute("success", "Project completed successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error completing project: " + e.getMessage());
        }
        return "redirect:/projects/" + id;
    }

    @PostMapping("/{id}/assign")
    public String assignMember(@PathVariable String id, @RequestParam String userId, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            String assignedById = authentication.getName();
            User assignedByUser = userRepository.findByUsername(assignedById).orElse(null);

            if (assignedByUser != null) {
                boolean success = projectService.assignMember(id, userId, assignedByUser.getId());
                if (success) {
                    redirectAttributes.addFlashAttribute("success", "Member assigned successfully");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Member is already assigned to this project");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error assigning member: " + e.getMessage());
        }
        return "redirect:/projects/" + id;
    }

    @PostMapping("/{id}/upload")
    public String uploadDocument(@PathVariable String id, @RequestParam MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Please select a file to upload");
                return "redirect:/projects/" + id;
            }

            String uploadedById = authentication.getName();
            User user = userRepository.findByUsername(uploadedById).orElse(null);

            if (user != null) {
                documentService.uploadDocument(id, file, user.getId());
                redirectAttributes.addFlashAttribute("success", "Document uploaded successfully");
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found");
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error uploading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Unexpected error: " + e.getMessage());
        }
        return "redirect:/projects/" + id;
    }
}

