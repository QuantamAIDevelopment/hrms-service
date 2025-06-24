package com.qaid.hrms.model.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class DepartmentResponseDTO {
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
    private List<DepartmentResponseDTO> subDepartments;
    private Integer employeeCount;
    private List<DesignationResponseDTO> designations;
}
