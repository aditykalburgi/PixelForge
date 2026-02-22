package com.pixelforge.nexus.service;

import com.pixelforge.nexus.dto.CreateProjectDTO;
import com.pixelforge.nexus.model.Project;
import com.pixelforge.nexus.model.ProjectMember;
import com.pixelforge.nexus.model.ProjectStatus;
import com.pixelforge.nexus.model.User;
import com.pixelforge.nexus.model.UserRole;
import com.pixelforge.nexus.repository.ProjectMemberRepository;
import com.pixelforge.nexus.repository.ProjectRepository;
import com.pixelforge.nexus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private UserRepository userRepository;

    public Project createProject(CreateProjectDTO dto, String createdById) {
        Project project = Project.builder()
            .name(dto.getName())
            .description(dto.getDescription())
            .deadline(dto.getDeadline())
            .createdById(createdById)
            .status(ProjectStatus.ACTIVE)
            .build();

        return projectRepository.save(project);
    }

    public Project completeProject(String projectId) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            project.setStatus(ProjectStatus.COMPLETED);
            return projectRepository.save(project);
        }
        return null;
    }

    public boolean assignMember(String projectId, String userId, String assignedById) {
        Optional<ProjectMember> existingMember = projectMemberRepository.findByProjectIdAndUserId(projectId, userId);
        if (existingMember.isPresent()) {
            return false;
        }

        ProjectMember member = ProjectMember.builder()
            .projectId(projectId)
            .userId(userId)
            .assignedById(assignedById)
            .build();

        projectMemberRepository.save(member);
        return true;
    }

    public List<Project> getProjectsForUser(String userId, String userRole) {
        if ("ADMIN".equals(userRole) || "PROJECT_LEAD".equals(userRole)) {
            return projectRepository.findAll();
        }

        List<ProjectMember> members = projectMemberRepository.findByUserId(userId);
        return members.stream()
            .map(member -> projectRepository.findById(member.getProjectId()).orElse(null))
            .filter(project -> project != null)
            .toList();
    }

    public Optional<Project> getProjectDetail(String projectId) {
        return projectRepository.findById(projectId);
    }

    public List<ProjectMember> getProjectMembers(String projectId) {
        return projectMemberRepository.findByProjectId(projectId);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Optional<Project> findById(String projectId) {
        return projectRepository.findById(projectId);
    }
}

