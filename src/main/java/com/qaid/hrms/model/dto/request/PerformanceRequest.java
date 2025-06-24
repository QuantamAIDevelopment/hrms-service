package com.qaid.hrms.model.dto.request;

import lombok.Data;

@Data
public class PerformanceRequest {
    private Long employeeId;
    private String period;
    private String summary;
    private Integer score;
}
