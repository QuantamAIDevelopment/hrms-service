package com.qaid.hrms.model.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class TimeSheetResponse {
    private Long id;
    private String employeeId;
    private String employeeName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer breakDuration;
    private Double totalHours;
    private String description;
    private String status;
    private String comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}