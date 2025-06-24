package com.qaid.hrms.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "performances")
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private Integer score;

    // Getters and Setters
}
