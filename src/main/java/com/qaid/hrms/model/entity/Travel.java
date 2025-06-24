package com.qaid.hrms.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "travels")
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee", nullable = false)
    private Employee employee;

    @Column(name = "travel_type", nullable = false, length = 50)
    private String travelType; // Business, Training, Conference, etc.

    @Column(name = "purpose", nullable = false)
    private String purpose;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "mode_of_travel", nullable = false, length = 50)
    private String modeOfTravel; // Air, Train, Bus, Car, etc.

    @Column(name = "estimated_cost", precision = 10, scale = 2)
    private BigDecimal estimatedCost;

    @Column(name = "actual_cost", precision = 10, scale = 2)
    private BigDecimal actualCost;

    @Column(name = "advance_amount", precision = 10, scale = 2)
    private BigDecimal advanceAmount;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "Pending";

    @Column
    private String comments;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}