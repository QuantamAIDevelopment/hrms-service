package com.qaid.hrms.model.dto.request;

import lombok.Data;

@Data
public class FlowRequest {
    private String name;
    private String description;
    private String status;
}
