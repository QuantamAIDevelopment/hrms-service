package com.qaid.hrms.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TimeTrackingRequest {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Check-in time is required")
    private LocalDateTime checkInTime;

    @NotNull(message = "Check-out time is required")
    private LocalDateTime checkOutTime;

    @NotNull(message = "Task description is required")
    private String taskDescription;
}
