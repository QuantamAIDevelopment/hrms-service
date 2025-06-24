package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.PerformanceRequest;
import com.qaid.hrms.model.dto.response.PerformanceResponse;
import com.qaid.hrms.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/performances")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;

    @PostMapping
    public ResponseEntity<PerformanceResponse> createPerformance(@RequestBody PerformanceRequest request) {
        return ResponseEntity.ok(performanceService.createPerformance(request));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<PerformanceResponse> updatePerformanceByEmployeeId(@PathVariable String employeeId, @RequestBody PerformanceRequest request) {
        return ResponseEntity.ok(performanceService.updatePerformanceByEmployeeId(employeeId, request));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deletePerformanceByEmployeeId(@PathVariable String employeeId) {
        performanceService.deletePerformanceByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<PerformanceResponse> getPerformanceByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(performanceService.getPerformanceByEmployeeId(employeeId));
    }

    @GetMapping
    public ResponseEntity<List<PerformanceResponse>> getAllPerformances() {
        return ResponseEntity.ok(performanceService.getAllPerformances());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PerformanceResponse>> getPerformancesByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(performanceService.getPerformancesByEmployee(employeeId));
    }
}
