package com.qaid.hrms.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceRequest {
    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "In time is required")
    private LocalDateTime inTime;

    private LocalDateTime outTime;
    private String note;
    private String status;

}