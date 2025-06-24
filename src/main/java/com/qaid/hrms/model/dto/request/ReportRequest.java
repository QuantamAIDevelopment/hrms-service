package com.qaid.hrms.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ReportRequest {
    @NotNull(message = "Report type is required")
    private String reportType;

    @NotNull(message = "Report name is required")
    private String reportName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private String parameters; // JSON string of report parameters
    private String description;

}