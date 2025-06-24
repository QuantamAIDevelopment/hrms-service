package com.qaid.hrms.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "flows")
public class Flow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    // Getters and Setters
}
