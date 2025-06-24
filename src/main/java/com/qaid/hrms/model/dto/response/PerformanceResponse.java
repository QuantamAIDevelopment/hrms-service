package com.qaid.hrms.model.dto.response;

import lombok.Data;

@Data
public class PerformanceResponse {
    private Long id;
    private Long employeeId;
    private String period;
    private String summary;
    private Integer score;
}
