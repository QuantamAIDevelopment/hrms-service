package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.SalaryRequest;
import com.qaid.hrms.model.dto.response.SalaryResponse;
import com.qaid.hrms.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/salaries")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    @PostMapping
    public ResponseEntity<SalaryResponse> createSalary(@RequestBody SalaryRequest request) {
        return ResponseEntity.ok(salaryService.createSalary(request));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<SalaryResponse> updateSalaryByEmployeeId(@PathVariable String employeeId, @RequestBody SalaryRequest request) {
        return ResponseEntity.ok(salaryService.updateSalaryByEmployeeId(employeeId, request));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteSalaryByEmployeeId(@PathVariable String employeeId) {
        salaryService.deleteSalaryByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<SalaryResponse> getSalaryByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(salaryService.getSalaryByEmployeeId(employeeId));
    }

    @GetMapping
    public ResponseEntity<List<SalaryResponse>> getAllSalaries() {
        return ResponseEntity.ok(salaryService.getAllSalaries());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SalaryResponse>> getSalariesByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(salaryService.getSalariesByEmployee(employeeId));
    }

    @GetMapping("/pay-period")
    public ResponseEntity<List<SalaryResponse>> getSalariesByPayPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(salaryService.getSalariesByPayPeriod(startDate, endDate));
    }

    @GetMapping("/employee/{employeeId}/pay-period")
    public ResponseEntity<List<SalaryResponse>> getSalariesByEmployeeAndPayPeriod(
            @PathVariable String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(salaryService.getSalariesByEmployeeAndPayPeriod(employeeId, startDate, endDate));
    }

    @PostMapping("/employee/{employeeId}/approve")
    public ResponseEntity<SalaryResponse> approveSalaryByEmployeeId(@PathVariable String employeeId, @RequestParam String comments) {
        return ResponseEntity.ok(salaryService.approveSalaryByEmployeeId(employeeId, comments));
    }

    @PostMapping("/employee/{employeeId}/reject")
    public ResponseEntity<SalaryResponse> rejectSalaryByEmployeeId(@PathVariable String employeeId, @RequestParam String comments) {
        return ResponseEntity.ok(salaryService.rejectSalaryByEmployeeId(employeeId, comments));
    }
}
