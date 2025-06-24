package com.qaid.hrms.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hr_policies")
public class HRPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(nullable = false)
    private String category;

    // Getters and Setters
}
