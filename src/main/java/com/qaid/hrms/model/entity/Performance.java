package com.qaid.hrms.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "performances")
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private Integer score;

    // Getters and Setters
}
