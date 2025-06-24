package com.qaid.hrms.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String userLevel;
    private List<String> userRoles;
    private String status;
    private boolean emailVerified;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}