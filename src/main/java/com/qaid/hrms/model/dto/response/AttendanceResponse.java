package com.qaid.hrms.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceResponse {
    private Long id;
    private String employeeId;
    private String employeeName;
    private LocalDateTime inTime;
    private LocalDateTime outTime;
    private String note;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}