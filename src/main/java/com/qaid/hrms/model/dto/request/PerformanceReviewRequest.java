package com.qaid.hrms.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PerformanceReviewRequest {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Reviewer is required")
    private String reviewer;

    @NotNull(message = "Review date is required")
    private LocalDate reviewDate;

    @NotNull(message = "Comments are required")
    private String comments;

    @NotNull(message = "Rating is required")
    private Integer rating;
}
