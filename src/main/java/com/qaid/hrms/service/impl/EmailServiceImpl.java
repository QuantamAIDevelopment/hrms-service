package com.qaid.hrms.service.impl;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.models.EmailAddress;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.SyncPoller;
import com.qaid.hrms.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailClient emailClient;

    @Value("${azure.communication.sender-address}")
    private String senderAddress;

    @Override
    public void sendVerificationEmail(String email, String token) {
        String content = String.format("""
            <html>
            <body>
                <h2>Email Verification</h2>
                <p>Please click the link below to verify your email address:</p>
                <p><a href="/verify-email?token=%s">Verify Email</a></p>
                <p>This link will expire in 24 hours.</p>
            </body>
            </html>
            """, token);

        sendEmail(email, "Email Verification", content);
    }

    @Override
    public void sendPasswordResetEmail(String email, String token) {
        String content = String.format("""
            <html>
            <body>
                <h2>Password Reset Request</h2>
                <p>Please click the link below to reset your password:</p>
                <p><a href="/reset-password?token=%s">Reset Password</a></p>
                <p>This link will expire in 24 hours.</p>
            </body>
            </html>
            """, token);

        sendEmail(email, "Password Reset Request", content);
    }

    @Override
    public void sendWelcomeEmail(String email, String username) {
        String content = String.format("""
            <html>
            <body>
                <h2>Welcome to HRMS!</h2>
                <p>Dear %s,</p>
                <p>Welcome to our HR Management System. We're excited to have you on board!</p>
                <p>You can now log in to your account and start using our services.</p>
            </body>
            </html>
            """, username);

        sendEmail(email, "Welcome to HRMS", content);
    }

    @Override
    public void sendNotificationEmail(String to, String subject, String content) {
        sendEmail(to, subject, content);
    }

    private void sendEmail(String to, String subject, String content) {
        EmailMessage message = new EmailMessage()
                .setSenderAddress(senderAddress)
                .setToRecipients(List.of(new EmailAddress(to)))
                .setSubject(subject)
                .setBodyHtml(content);

        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(message);
        poller.waitForCompletion();
    }
} 