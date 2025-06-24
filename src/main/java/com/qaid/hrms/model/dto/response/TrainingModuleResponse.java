package com.qaid.hrms.model.dto.response;

import lombok.Data;

@Data
public class TrainingModuleResponse {

    private Long id;
    private String title;
    private String description;
    private String trainer;
    private String duration;
}
