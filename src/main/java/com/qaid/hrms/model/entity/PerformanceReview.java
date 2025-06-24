package com.qaid.hrms.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "performance_reviews")
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private String reviewer;

    @Column(nullable = false)
    private LocalDate reviewDate;

    @Column(nullable = false)
    private String comments;

    @Column(nullable = false)
    private Integer rating;

}
