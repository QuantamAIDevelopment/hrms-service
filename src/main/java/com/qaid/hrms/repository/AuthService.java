package com.qaid.hrms.repository;

import com.qaid.hrms.model.dto.request.LoginRequest;
import com.qaid.hrms.model.dto.request.PasswordChangeRequest;
import com.qaid.hrms.model.dto.request.PasswordResetRequest;
import com.qaid.hrms.model.dto.request.UserRequest;
import com.qaid.hrms.model.dto.response.JwtResponse;
import com.qaid.hrms.model.dto.response.UserResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
    UserResponse register(UserRequest userRequest);
    JwtResponse refreshToken(String refreshToken);
    void logout(String refreshToken);
    void changePassword(PasswordChangeRequest request);
    void forgotPassword(String email);
    void resetPassword(PasswordResetRequest request);
    void verifyEmail(String token);
    void resendVerificationEmail(String email);
    UserResponse getCurrentUser();
    void updateUserProfile(UserRequest userRequest);
} 