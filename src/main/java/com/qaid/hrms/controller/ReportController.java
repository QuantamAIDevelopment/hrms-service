package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.ReportRequest;
import com.qaid.hrms.model.dto.response.ReportResponse;
import com.qaid.hrms.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponse> generateReport(@RequestBody ReportRequest request) throws IOException {
        return ResponseEntity.ok(reportService.generateReport(request));
    }

    @GetMapping("/{allocatedId}")
    public ResponseEntity<ReportResponse> getReportByAllocatedId(@PathVariable String allocatedId) {
        return ResponseEntity.ok(reportService.getReportByAllocatedId(allocatedId));
    }

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/type/{reportType}")
    public ResponseEntity<List<ReportResponse>> getReportsByType(@PathVariable String reportType) {
        return ResponseEntity.ok(reportService.getReportsByType(reportType));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ReportResponse>> getReportsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getReportsByDateRange(startDate, endDate));
    }

    @GetMapping("/type/{reportType}/date-range")
    public ResponseEntity<List<ReportResponse>> getReportsByTypeAndDateRange(
            @PathVariable String reportType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getReportsByTypeAndDateRange(reportType, startDate, endDate));
    }

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
