package com.pixelforge.nexus.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // BCrypt strength=12 per OWASP recommendation for password hashing
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for development/REST API
            .cors(cors -> cors.configure(http)) // Enable CORS
            .authenticationProvider(authenticationProvider())

            // Authorization rules
            .authorizeHttpRequests(authz -> authz
                // Permit unauthenticated access to login, auth info, and static resources
                .requestMatchers("/login", "/api/auth/**", "/css/**", "/js/**").permitAll()

                // ADMIN only: administrative endpoints
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // ADMIN only: create and complete projects
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/projects/create").hasRole("ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/projects/*/complete").hasRole("ADMIN")

                // ADMIN or PROJECT_LEAD: assign members and upload documents
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/projects/*/assign").hasAnyRole("ADMIN", "PROJECT_LEAD")
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/projects/*/upload").hasAnyRole("ADMIN", "PROJECT_LEAD")

                // All other API endpoints require authentication
                .requestMatchers("/api/**").authenticated()
                
                // Permit everything else for now (like React routes if served by Spring)
                .anyRequest().permitAll()
            )

            // Login configuration
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    response.setStatus(200);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": true, \"message\": \"Logged in successfully\"}");
                })
                .failureHandler((request, response, exception) -> {
                    response.setStatus(401);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": false, \"message\": \"Login failed: " + exception.getMessage() + "\"}");
                })
                .permitAll()
            )

            // Logout configuration
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(200);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": true, \"message\": \"Logged out successfully\"}");
                })
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}

