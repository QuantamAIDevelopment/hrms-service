package com.qaid.hrms.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SalaryRequest {
    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Pay period start date is required")
    private LocalDate payPeriodStart;

    @NotNull(message = "Pay period end date is required")
    private LocalDate payPeriodEnd;

    @NotNull(message = "Basic salary is required")
    @Positive(message = "Basic salary must be positive")
    private BigDecimal basicSalary;

    private BigDecimal allowances;
    private BigDecimal deductions;
    private LocalDate paymentDate;
    private String comments;


}