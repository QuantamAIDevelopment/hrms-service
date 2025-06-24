package com.qaid.hrms.model.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaveRequestRequestDTO {
    private Long employeeId;
    private Long leaveTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}
