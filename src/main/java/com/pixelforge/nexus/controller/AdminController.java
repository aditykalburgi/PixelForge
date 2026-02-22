package com.pixelforge.nexus.controller;

import com.pixelforge.nexus.dto.CreateUserDTO;
import com.pixelforge.nexus.model.User;
import com.pixelforge.nexus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        try {
            List<User> users = userService.loadAll();
            model.addAttribute("users", users);
            return "admin/users";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading users: " + e.getMessage());
            return "admin/users";
        }
    }

    @PostMapping("/users/create")
    public String createUser(@Valid @ModelAttribute CreateUserDTO dto, RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(dto);
            redirectAttributes.addFlashAttribute("success", "User created successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/role")
    public String updateUserRole(@PathVariable String id, @RequestParam String role, RedirectAttributes redirectAttributes) {
        try {
            userService.updateRole(id, role);
            redirectAttributes.addFlashAttribute("success", "User role updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating user role: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
}

