package com.qaid.hrms.model.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "employment_status")
public class EmploymentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column
    private String description;
}