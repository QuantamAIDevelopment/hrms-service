package com.qaid.hrms.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TrainingModuleRequest {

    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Trainer is required")
    private String trainer;

    @NotNull(message = "Duration is required")
    private String duration;
}
