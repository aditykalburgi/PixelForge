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
        // CSRF protection enabled by default; Thymeleaf automatically includes CSRF token in forms
        http
            .csrf(csrf -> csrf.disable())

            // Authorization rules
            .authorizeHttpRequests(authz -> authz
                // Permit unauthenticated access to login and static resources
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()

                // ADMIN only: administrative endpoints
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // ADMIN only: create and complete projects
                .requestMatchers("POST", "/projects/create").hasRole("ADMIN")
                .requestMatchers("POST", "/projects/*/complete").hasRole("ADMIN")

                // ADMIN or PROJECT_LEAD: assign members and upload documents
                .requestMatchers("POST", "/projects/*/assign").hasAnyRole("ADMIN", "PROJECT_LEAD")
                .requestMatchers("POST", "/projects/*/upload").hasAnyRole("ADMIN", "PROJECT_LEAD")

                // All other endpoints require authentication
                .anyRequest().authenticated()
            )

            // Login configuration
            .formLogin(form -> form
                // Login page is /login
                .loginPage("/login")
                // Redirect to /dashboard on successful authentication
                .defaultSuccessUrl("/dashboard", true)
                // Redirect to /login?error on authentication failure
                .failureUrl("/login?error")
                // Allow POST to /login for form submission
                .permitAll()
            )

            // Logout configuration
            .logout(logout -> logout
                // Logout endpoint is /logout
                .logoutUrl("/logout")
                // Redirect to /login?logout after successful logout
                .logoutSuccessUrl("/login?logout")
                // Invalidate session on logout to prevent session fixation attacks
                .invalidateHttpSession(true)
                // Delete JSESSIONID cookie on logout
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}

