package com.qaid.hrms.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaid.hrms.exception.BadRequestException;
import com.qaid.hrms.exception.ResourceNotFoundException;
import com.qaid.hrms.exception.UnauthorizedException;
import com.qaid.hrms.model.dto.request.LoginRequest;
import com.qaid.hrms.model.dto.request.PasswordChangeRequest;
import com.qaid.hrms.model.dto.request.PasswordResetRequest;
import com.qaid.hrms.model.dto.request.UserRequest;
import com.qaid.hrms.model.dto.response.JwtResponse;
import com.qaid.hrms.model.dto.response.UserResponse;
import com.qaid.hrms.model.entity.User;
import com.qaid.hrms.repository.UserRepository;
import com.qaid.hrms.security.JwtService;
import com.qaid.hrms.service.AuthService;
import com.qaid.hrms.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
        try {
            // First check if the user exists
            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + loginRequest.getUsername()));
            
            // Then attempt authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Update last login timestamp
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            String accessToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateToken(user);

            return new JwtResponse(accessToken, refreshToken, user.getUsername(), user.getUserLevel());
        } catch (ResourceNotFoundException e) {
            throw new UnauthorizedException("User not found: " + e.getMessage());
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Invalid password for user: " + loginRequest.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException("Login failed: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserResponse register(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BadRequestException("Email is already taken");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setUserLevel(userRequest.getUserLevel());
        user.setUserRoles(userRequest.getUserRoles());
        user.setStatus("Active");
        user.setEmailVerified(false);

        // Generate verification token
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpiry(LocalDateTime.now().plusHours(24));

        user = userRepository.save(user);
//        emailService.sendVerificationEmail(user.getEmail(), verificationToken);

        return mapToResponse(user);
    }

    @Override
    @Transactional
    public JwtResponse refreshToken(String refreshToken) {
        try {
            String username = jwtService.extractUserName(refreshToken);
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username, "", new ArrayList<>());
            if (!jwtService.validateToken(refreshToken, userDetails)) {
                throw new UnauthorizedException("Invalid refresh token");
            }
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            // Check if user is active
            if (!"Active".equals(user.getStatus())) {
                throw new UnauthorizedException("User account is not active");
            }

            String accessToken = jwtService.generateToken(user);
            String newRefreshToken = jwtService.generateToken(user);

            return new JwtResponse(accessToken, newRefreshToken, user.getUsername(), user.getUserLevel());
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid refresh token");
        }
    }

    @Override
    public void logout(String refreshToken) {
        SecurityContextHolder.clearContext();
    }

    @Override
    @Transactional
    public void changePassword(PasswordChangeRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if user is active
        if (!"Active".equals(user.getStatus())) {
            throw new BadRequestException("User account is not active");
        }

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(24));
        userRepository.save(user);

        emailService.sendPasswordResetEmail(email, resetToken);
    }

    @Override
    @Transactional
    public void resetPassword(PasswordResetRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid reset token"));

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Reset token has expired");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid verification token"));

        if (user.getVerificationTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Verification token has expired");
        }

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpiry(null);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void resendVerificationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.isEmailVerified()) {
            throw new BadRequestException("Email is already verified");
        }

        // Check if user is active
        if (!"Active".equals(user.getStatus())) {
            throw new BadRequestException("User account is not active");
        }

        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpiry(LocalDateTime.now().plusHours(24));
        userRepository.save(user);

        emailService.sendVerificationEmail(email, verificationToken);
    }

    @Override
    @Transactional
    public UserResponse getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToResponse(user);
    }

    @Override
    @Transactional
    public void updateUserProfile(UserRequest userRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if user is active
        if (!"Active".equals(user.getStatus())) {
            throw new BadRequestException("User account is not active");
        }

        if (userRequest.getEmail() != null && !userRequest.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userRequest.getEmail())) {
                throw new BadRequestException("Email is already taken");
            }
            user.setEmail(userRequest.getEmail());
            user.setEmailVerified(false);
            
            // Generate new verification token for email change
            String verificationToken = UUID.randomUUID().toString();
            user.setVerificationToken(verificationToken);
            user.setVerificationTokenExpiry(LocalDateTime.now().plusHours(24));
            emailService.sendVerificationEmail(user.getEmail(), verificationToken);
        }

        if (userRequest.getUserLevel() != null) {
            user.setUserLevel(userRequest.getUserLevel());
        }

        if (userRequest.getUserRoles() != null) {
            user.setUserRoles(userRequest.getUserRoles());
        }

        userRepository.save(user);
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setUserLevel(user.getUserLevel());
        response.setUserRoles(user.getUserRoles());
        response.setStatus(user.getStatus());
        response.setEmailVerified(user.isEmailVerified());
        response.setLastLogin(user.getLastLogin());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
} 