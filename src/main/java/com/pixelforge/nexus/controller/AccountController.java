package com.pixelforge.nexus.controller;

import com.pixelforge.nexus.dto.ChangePasswordDTO;
import com.pixelforge.nexus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> account(Authentication authentication) {
        String username = authentication.getName();
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO dto, Authentication authentication) {
        try {
            if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
                return ResponseEntity.status(400).body(java.util.Map.of("error", "New passwords do not match"));
            }

            String username = authentication.getName();
            com.pixelforge.nexus.model.User user = userService.findByUsername(username).orElse(null);

            if (user != null) {
                boolean success = userService.changePassword(user.getId(), dto.getCurrentPassword(), dto.getNewPassword());
                if (success) {
                    return ResponseEntity.ok(java.util.Map.of("message", "Password changed successfully"));
                } else {
                    return ResponseEntity.status(400).body(java.util.Map.of("error", "Current password is incorrect"));
                }
            } else {
                return ResponseEntity.status(404).body(java.util.Map.of("error", "User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Map.of("error", "Error changing password: " + e.getMessage()));
        }
    }
}

