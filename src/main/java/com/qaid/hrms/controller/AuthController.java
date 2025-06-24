package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.LoginRequest;
import com.qaid.hrms.model.dto.request.PasswordChangeRequest;
import com.qaid.hrms.model.dto.request.PasswordResetRequest;
import com.qaid.hrms.model.dto.request.UserRequest;
import com.qaid.hrms.model.dto.response.JwtResponse;
import com.qaid.hrms.model.dto.response.UserResponse;
import com.qaid.hrms.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Simple in-memory rate limiter (per IP)
    private final Map<String, RateLimitInfo> loginAttempts = new ConcurrentHashMap<>();
    private static final int MAX_ATTEMPTS = 5;
    private static final long WINDOW_MS = 60_000; // 1 minute

    private static class RateLimitInfo {
        int attempts;
        long windowStart;
    }

    @Operation(summary = "Login", description = "Login with username and password")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        RateLimitInfo info = loginAttempts.computeIfAbsent(clientIp, k -> new RateLimitInfo());
        long now = System.currentTimeMillis();
        if (now - info.windowStart > WINDOW_MS) {
            info.windowStart = now;
            info.attempts = 0;
        }
        if (++info.attempts > MAX_ATTEMPTS) {
            return ResponseEntity.status(429).build(); // Too Many Requests
        }
        try {
            return ResponseEntity.ok(authService.login(loginRequest));
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            throw e;
        }
    }

    @Operation(summary = "Register", description = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authService.register(userRequest));
    }

    @Operation(summary = "Refresh Token", description = "Get a new access token using refresh token")
    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@RequestParam String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @Operation(summary = "Logout", description = "Logout and invalidate refresh token")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Change Password", description = "Change user password")
    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        authService.changePassword(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Forgot Password", description = "Request password reset")
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
        authService.forgotPassword(email);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reset Password", description = "Reset password with token")
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody PasswordResetRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Verify Email", description = "Verify user email with token")
    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Resend Verification Email", description = "Resend verification email to the user")
    @PostMapping("/resend-verification-email")
    public ResponseEntity<Void> resendVerificationEmail(@RequestParam String email) {
        authService.resendVerificationEmail(email);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Current User", description = "Get the profile of the logged-in user")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }

    @Operation(summary = "Update User Profile", description = "Update the profile of the logged-in user")
    @PutMapping("/me")
    public ResponseEntity<Void> updateUserProfile(@RequestBody UserRequest request) {
        authService.updateUserProfile(request);
        return ResponseEntity.ok().build();
    }
}