package com.qaid.hrms.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "calendars")
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate eventDate;

    @Column
    private String description;

    @Column
    private String location;

    @Enumerated(EnumType.STRING)
    private CalendarEventType type;

    // Getters and Setters
}
