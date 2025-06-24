package com.qaid.hrms.model.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaveRequestResponseDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private Long leaveTypeId;
    private String leaveTypeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalDays;
    private int usedDays;
    private int remainingDays;
    private String reason;
    private String status;
    private Long approvedById;
    private String approverName;
    private String rejectionReason;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String departmentName;
}
