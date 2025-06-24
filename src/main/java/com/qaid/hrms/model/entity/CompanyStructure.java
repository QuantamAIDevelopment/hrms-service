package com.qaid.hrms.model.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "company_structures")
public class CompanyStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent")
    private CompanyStructure parent;

    @ManyToOne
    @JoinColumn(name = "head")
    private Employee head;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    @Column(length = 50)
    private String timezone;
}