package com.qaid.hrms.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vibes")
public class Vibe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int score;

    // Getters and Setters
}
