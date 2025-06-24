package com.qaid.hrms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignationDTO {

    private Long id;
    private String title;
    private String description;
    private Integer level;
    private Boolean isActive = true;
    private Long departmentId;
    private String departmentName;
    private String jobDescription;
    private String requirements;
    private String code;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
