package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.CalendarEventRequest;
import com.qaid.hrms.model.dto.response.CalendarEventResponse;
import com.qaid.hrms.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    @PostMapping("/events")
    public ResponseEntity<CalendarEventResponse> createEvent(@RequestBody CalendarEventRequest request) {
        return ResponseEntity.ok(calendarService.createEvent(request));
    }

    @PutMapping("/events/{allocatedId}")
    public ResponseEntity<CalendarEventResponse> updateEventByAllocatedId(@PathVariable String allocatedId, @RequestBody CalendarEventRequest request) {
        return ResponseEntity.ok(calendarService.updateEventByAllocatedId(allocatedId, request));
    }

    @DeleteMapping("/events/{allocatedId}")
    public ResponseEntity<Void> deleteEventByAllocatedId(@PathVariable String allocatedId) {
        calendarService.deleteEventByAllocatedId(allocatedId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/events/{allocatedId}")
    public ResponseEntity<CalendarEventResponse> getEventByAllocatedId(@PathVariable String allocatedId) {
        return ResponseEntity.ok(calendarService.getEventByAllocatedId(allocatedId));
    }

    @GetMapping("/events")
    public ResponseEntity<List<CalendarEventResponse>> getAllEvents() {
        return ResponseEntity.ok(calendarService.getAllEvents());
    }

    @GetMapping("/events/date")
    public ResponseEntity<List<CalendarEventResponse>> getEventsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(calendarService.getEventsByDate(date));
    }

    @GetMapping("/events/type/{type}")
    public ResponseEntity<List<CalendarEventResponse>> getEventsByType(@PathVariable String type) {
        return ResponseEntity.ok(calendarService.getEventsByType(type));
    }
}
