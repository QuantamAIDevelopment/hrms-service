package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.TimeTrackingRequest;
import com.qaid.hrms.model.dto.response.TimeTrackingResponse;
import com.qaid.hrms.service.TimeTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/timetracking")
@RequiredArgsConstructor
public class TimeTrackingController {
    private final TimeTrackingService timeTrackingService;

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PostMapping
    public ResponseEntity<TimeTrackingResponse> createTimeTracking(@RequestBody TimeTrackingRequest request) {
        return ResponseEntity.ok(timeTrackingService.createTimeTracking(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/{employeeId}")
    public ResponseEntity<TimeTrackingResponse> updateTimeTrackingByEmployeeId(@PathVariable String employeeId, @RequestBody TimeTrackingRequest request) {
        return ResponseEntity.ok(timeTrackingService.updateTimeTrackingByEmployeeId(employeeId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteTimeTrackingByEmployeeId(@PathVariable String employeeId) {
        timeTrackingService.deleteTimeTrackingByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/{employeeId}")
    public ResponseEntity<TimeTrackingResponse> getTimeTrackingByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(timeTrackingService.getTimeTrackingByEmployeeId(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TimeTrackingResponse>> getTimeTrackingByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(timeTrackingService.getTimeTrackingByEmployee(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<TimeTrackingResponse>> getAllTimeTrackingRecords() {
        return ResponseEntity.ok(timeTrackingService.getAllTimeTrackingRecords());
    }
}
