package com.qaid.hrms.model.dto.request;

import lombok.Data;

@Data
public class DesignationRequestDTO {
    private String title;
    private String description;
    private Integer level;
    private Boolean isActive;
    private Long departmentId;
    private String jobDescription;
    private String requirements;
    private String code;
}
