package com.qaid.hrms.model.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TimeTrackingResponse {

    private Long id;
    private Long employeeId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String taskDescription;
}
