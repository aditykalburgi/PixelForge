package com.pixelforge.nexus.security;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {
    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCK_DURATION_MINUTES = 15;

    private final ConcurrentHashMap<String, LoginAttempt> loginAttempts = new ConcurrentHashMap<>();

    public void loginFailed(String ip) {
        LoginAttempt attempt = loginAttempts.getOrDefault(ip, new LoginAttempt());
        attempt.incrementFailedAttempts();
        attempt.setLastAttemptTime(LocalDateTime.now());
        loginAttempts.put(ip, attempt);
    }

    public void loginSucceeded(String ip) {
        loginAttempts.remove(ip);
    }

    public boolean isBlocked(String ip) {
        LoginAttempt attempt = loginAttempts.get(ip);
        if (attempt == null) {
            return false;
        }

        if (attempt.getFailedAttempts() >= MAX_ATTEMPTS) {
            LocalDateTime lockTime = attempt.getLastAttemptTime();
            LocalDateTime now = LocalDateTime.now();
            long minutesElapsed = ChronoUnit.MINUTES.between(lockTime, now);

            if (minutesElapsed < LOCK_DURATION_MINUTES) {
                return true;
            } else {
                loginAttempts.remove(ip);
                return false;
            }
        }

        return false;
    }

    public static class LoginAttempt {
        private int failedAttempts = 0;
        private LocalDateTime lastAttemptTime;

        public void incrementFailedAttempts() {
            this.failedAttempts++;
        }

        public int getFailedAttempts() {
            return failedAttempts;
        }

        public void setLastAttemptTime(LocalDateTime lastAttemptTime) {
            this.lastAttemptTime = lastAttemptTime;
        }

        public LocalDateTime getLastAttemptTime() {
            return lastAttemptTime;
        }
    }
}

