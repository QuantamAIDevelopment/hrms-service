package com.qaid.hrms.model.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReportResponse {
    private Long id;
    private String reportType;
    private String reportName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String filePath;
    private String fileName;
    private Long fileSize;
    private String mimeType;
    private String parameters;
    private String description;
    private String generatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}