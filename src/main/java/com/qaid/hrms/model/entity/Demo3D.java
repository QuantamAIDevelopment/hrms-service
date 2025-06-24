package com.qaid.hrms.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "demos")
public class Demo3D {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String modelUrl;

    // Getters and Setters
}
