package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.ReportRequest;
import com.qaid.hrms.model.dto.response.ReportResponse;
import com.qaid.hrms.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @PostMapping
    public ResponseEntity<ReportResponse> generateReport(@RequestBody ReportRequest request) throws IOException {
        return ResponseEntity.ok(reportService.generateReport(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/{allocatedId}")
    public ResponseEntity<ReportResponse> getReportByAllocatedId(@PathVariable String allocatedId) {
        return ResponseEntity.ok(reportService.getReportByAllocatedId(allocatedId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/type/{reportType}")
    public ResponseEntity<List<ReportResponse>> getReportsByType(@PathVariable String reportType) {
        return ResponseEntity.ok(reportService.getReportsByType(reportType));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/date-range")
    public ResponseEntity<List<ReportResponse>> getReportsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getReportsByDateRange(startDate, endDate));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/type/{reportType}/date-range")
    public ResponseEntity<List<ReportResponse>> getReportsByTypeAndDateRange(
            @PathVariable String reportType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getReportsByTypeAndDateRange(reportType, startDate, endDate));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/download/{allocatedId}")
    public ResponseEntity<byte[]> downloadReportByAllocatedId(@PathVariable String allocatedId) {
        return ResponseEntity.ok(reportService.downloadReportByAllocatedId(allocatedId));
    }

    @DeleteMapping("/{allocatedId}")
    public ResponseEntity<Void> deleteReportByAllocatedId(@PathVariable String allocatedId) {
        reportService.deleteReportByAllocatedId(allocatedId);
        return ResponseEntity.noContent().build();
    }
}
