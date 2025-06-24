package com.qaid.hrms.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

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

    // Getters and Setters
}
