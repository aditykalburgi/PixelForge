package com.pixelforge.nexus.controller;

import com.pixelforge.nexus.dto.ChangePasswordDTO;
import com.pixelforge.nexus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String account() {
        return "account";
    }

    @PostMapping("/password")
    public String changePassword(@Valid @ModelAttribute ChangePasswordDTO dto, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
                redirectAttributes.addFlashAttribute("error", "New passwords do not match");
                return "redirect:/account";
            }

            String username = authentication.getName();
            com.pixelforge.nexus.model.User user = userService.findByUsername(username).orElse(null);

            if (user != null) {
                boolean success = userService.changePassword(user.getId(), dto.getCurrentPassword(), dto.getNewPassword());
                if (success) {
                    redirectAttributes.addFlashAttribute("success", "Password changed successfully");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Current password is incorrect");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error changing password: " + e.getMessage());
        }
        return "redirect:/account";
    }
}

