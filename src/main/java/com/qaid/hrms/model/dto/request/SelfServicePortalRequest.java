package com.qaid.hrms.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SelfServicePortalRequest {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Feature is required")
    private String feature;

    @NotNull(message = "Description is required")
    private String description;
}
