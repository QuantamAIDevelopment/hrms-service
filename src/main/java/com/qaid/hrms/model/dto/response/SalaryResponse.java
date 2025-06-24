package com.qaid.hrms.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SalaryResponse {
    private Long id;
    private String employeeId;
    private String employeeName;
    private LocalDate payPeriodStart;
    private LocalDate payPeriodEnd;
    private BigDecimal basicSalary;
    private BigDecimal allowances;
    private BigDecimal deductions;
    private BigDecimal netSalary;
    private LocalDate paymentDate;
    private String status;
    private String comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}