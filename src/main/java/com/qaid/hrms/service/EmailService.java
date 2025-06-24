package com.qaid.hrms.service;

public interface EmailService {
    void sendVerificationEmail(String email, String token);
    void sendPasswordResetEmail(String email, String token);
    void sendWelcomeEmail(String email, String username);
    void sendNotificationEmail(String to, String subject, String content);
} 