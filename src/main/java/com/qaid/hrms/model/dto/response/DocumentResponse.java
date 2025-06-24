package com.qaid.hrms.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentResponse {
    private Long id;
    private String employeeId;
    private String employeeName;
    private String fileName;
    private String filePath;
    private String mimeType;
    private Long fileSize;
    private String documentType;
    private String description;
    private String uploadedBy;
    private String status;
    private LocalDateTime expiryDate;
    private LocalDateTime uploadedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}