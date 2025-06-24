package com.qaid.hrms.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "leave_balances")
public class LeaveBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "leave_type_id", nullable = false)
    private LeaveType leaveType;

    @Column(nullable = false)
    private Integer totalDays;

    @Column(nullable = false)
    private Integer usedDays = 0;

    @Column(nullable = false)
    private Integer remainingDays;

    @Column(nullable = false)
    private Integer year;

    @PrePersist
    @PreUpdate
    public void calculateRemainingDays() {
        this.remainingDays = this.totalDays - this.usedDays;
    }
} 