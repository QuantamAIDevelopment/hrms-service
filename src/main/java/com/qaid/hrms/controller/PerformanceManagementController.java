package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.PerformanceReviewRequest;
import com.qaid.hrms.model.dto.response.PerformanceReviewResponse;
import com.qaid.hrms.service.PerformanceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/performance-reviews")
@RequiredArgsConstructor
public class PerformanceManagementController {

    private final PerformanceManagementService performanceManagementService;

    @PostMapping
    public ResponseEntity<PerformanceReviewResponse> createPerformanceReview(@RequestBody PerformanceReviewRequest request) {
        return ResponseEntity.ok(performanceManagementService.createPerformanceReview(request));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<PerformanceReviewResponse> updatePerformanceReviewByEmployeeId(@PathVariable String employeeId, @RequestBody PerformanceReviewRequest request) {
        return ResponseEntity.ok(performanceManagementService.updatePerformanceReviewByEmployeeId(employeeId, request));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deletePerformanceReviewByEmployeeId(@PathVariable String employeeId) {
        performanceManagementService.deletePerformanceReviewByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<PerformanceReviewResponse> getPerformanceReviewByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(performanceManagementService.getPerformanceReviewByEmployeeId(employeeId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PerformanceReviewResponse>> getPerformanceReviewsByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(performanceManagementService.getPerformanceReviewsByEmployee(employeeId));
    }

    @GetMapping
    public ResponseEntity<List<PerformanceReviewResponse>> getAllPerformanceReviews() {
        return ResponseEntity.ok(performanceManagementService.getAllPerformanceReviews());
    }
}
