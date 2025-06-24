package com.qaid.hrms.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "training_modules")
public class TrainingModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String trainer;

    @Column(nullable = false)
    private String duration;

    // Getters and Setters
}
