package com.qaid.hrms.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "self_service_portals")
public class SelfServicePortal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private String feature;

    @Column(nullable = false)
    private String description;

    // Getters and Setters
}
