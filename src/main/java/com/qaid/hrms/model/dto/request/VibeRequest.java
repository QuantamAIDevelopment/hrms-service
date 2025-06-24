package com.qaid.hrms.model.dto.request;

import lombok.Data;

@Data
public class VibeRequest {
    private String title;
    private String description;
    private int score;
}
