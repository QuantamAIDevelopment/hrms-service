package com.qaid.hrms.model.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DesignationResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Integer level;
    private Boolean isActive;
    private Long departmentId;
    private String departmentName;
    private String jobDescription;
    private String requirements;
    private String code;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
