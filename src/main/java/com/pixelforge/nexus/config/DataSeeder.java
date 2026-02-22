package com.pixelforge.nexus.config;

import com.pixelforge.nexus.model.User;
import com.pixelforge.nexus.model.UserRole;
import com.pixelforge.nexus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                .username("admin")
                .email("admin@pixelforge.local")
                .passwordHash(passwordEncoder.encode("Admin123!"))
                .role(UserRole.ADMIN)
                .build();

            User lead = User.builder()
                .username("lead1")
                .email("lead1@pixelforge.local")
                .passwordHash(passwordEncoder.encode("Lead123!"))
                .role(UserRole.PROJECT_LEAD)
                .build();

            User dev = User.builder()
                .username("dev1")
                .email("dev1@pixelforge.local")
                .passwordHash(passwordEncoder.encode("Dev123!"))
                .role(UserRole.DEVELOPER)
                .build();

            userRepository.save(admin);
            userRepository.save(lead);
            userRepository.save(dev);

            System.out.println("DataSeeder: Initial users created successfully");
        }
    }
}

