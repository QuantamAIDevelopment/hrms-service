package com.qaid.hrms.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TravelRequest {
    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Travel type is required")
    private String travelType;

    @NotNull(message = "Purpose is required")
    private String purpose;

    @NotNull(message = "Start date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @NotNull(message = "Destination is required")
    private String destination;

    @NotNull(message = "Mode of travel is required")
    private String modeOfTravel;

    @Positive(message = "Estimated cost must be positive")
    private BigDecimal estimatedCost;

    private BigDecimal advanceAmount;
    private String comments;

}