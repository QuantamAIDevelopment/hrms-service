package com.qaid.hrms.model.dto.response;

import lombok.Data;

@Data
public class HRPolicyResponse {
    private Long id;
    private String title;
    private String content;
    private String category;
}
