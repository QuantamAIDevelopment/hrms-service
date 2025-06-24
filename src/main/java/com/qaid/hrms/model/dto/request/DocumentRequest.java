package com.qaid.hrms.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class DocumentRequest {
    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Document type is required")
    private String documentType;

    @NotNull(message = "File is required")
    private MultipartFile file;

    private String description;
    private LocalDateTime expiryDate;

}