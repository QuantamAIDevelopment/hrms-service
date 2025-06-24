package com.qaid.hrms.model.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PerformanceReviewResponse {

    private Long id;
    private Long employeeId;
    private String reviewer;
    private LocalDate reviewDate;
    private String comments;
    private Integer rating;
}
