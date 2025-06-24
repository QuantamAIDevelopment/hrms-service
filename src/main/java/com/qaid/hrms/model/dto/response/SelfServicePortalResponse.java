package com.qaid.hrms.model.dto.response;

import lombok.Data;

@Data
public class SelfServicePortalResponse {

    private Long id;
    private Long employeeId;
    private String feature;
    private String description;
}
