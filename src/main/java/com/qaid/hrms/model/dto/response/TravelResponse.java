package com.qaid.hrms.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TravelResponse {
    private Long id;
    private String employeeId;
    private String employeeName;
    private String travelType;
    private String purpose;
    private LocalDate startDate;
    private LocalDate endDate;
    private String destination;
    private String modeOfTravel;
    private BigDecimal estimatedCost;
    private BigDecimal actualCost;
    private BigDecimal advanceAmount;
    private String status;
    private String comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}