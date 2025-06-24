package com.qaid.hrms.controller;

import com.qaid.hrms.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send-verification")
    public ResponseEntity<Void> sendVerificationEmail(@RequestParam String email, @RequestParam String token) {
        emailService.sendVerificationEmail(email, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send-password-reset")
    public ResponseEntity<Void> sendPasswordResetEmail(@RequestParam String email, @RequestParam String token) {
        emailService.sendPasswordResetEmail(email, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send-welcome")
    public ResponseEntity<Void> sendWelcomeEmail(@RequestParam String email, @RequestParam String username) {
        emailService.sendWelcomeEmail(email, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send-notification")
    public ResponseEntity<Void> sendNotificationEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String content) {
        emailService.sendNotificationEmail(to, subject, content);
        return ResponseEntity.ok().build();
    }
}
