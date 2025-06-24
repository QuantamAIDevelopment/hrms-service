package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.UserRequest;
import com.qaid.hrms.model.dto.response.UserResponse;
import com.qaid.hrms.model.entity.User;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(Long id, UserRequest userRequest);
    void deleteUser(Long id);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 