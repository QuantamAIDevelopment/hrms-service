package com.qaid.hrms.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DepartmentDTO {
    private Long id;
    private String name;
    private String description;
    private String code;
    private String location;
    private Boolean isActive;
    private Long parentDepartmentId;
    private String parentDepartmentName;
    private Long managerId;
    private String managerName;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private List<DepartmentDTO> subDepartments;
    private Integer employeeCount;
    private List<DesignationDTO> designations;
} 