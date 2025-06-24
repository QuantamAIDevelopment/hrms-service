package com.qaid.hrms.model.dto.response;

import lombok.Data;

@Data
public class VibeResponse {
    private Long id;
    private String title;
    private String description;
    private int score;
}
