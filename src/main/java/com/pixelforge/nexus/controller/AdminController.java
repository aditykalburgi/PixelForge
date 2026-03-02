package com.pixelforge.nexus.controller;

import com.pixelforge.nexus.dto.CreateUserDTO;
import com.pixelforge.nexus.model.User;
import com.pixelforge.nexus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> listUsers() {
        try {
            List<User> users = userService.loadAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Map.of("error", "Error loading users: " + e.getMessage()));
        }
    }

    @PostMapping("/users/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO dto) {
        try {
            userService.createUser(dto);
            return ResponseEntity.ok(java.util.Map.of("message", "User created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Map.of("error", "Error creating user: " + e.getMessage()));
        }
    }

    @PostMapping("/users/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable String id, @RequestParam String role) {
        try {
            userService.updateRole(id, role);
            return ResponseEntity.ok(java.util.Map.of("message", "User role updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Map.of("error", "Error updating user role: " + e.getMessage()));
        }
    }
}

