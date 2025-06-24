package com.qaid.hrms.model.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class DepartmentRequestDTO {
    private String name;
    private String description;
    private String code;
    private String location;
    private Boolean isActive;
    private Long parentDepartmentId;
    private Long managerId;
    private List<Long> subDepartmentIds;
    private List<Long> designationIds;
}
