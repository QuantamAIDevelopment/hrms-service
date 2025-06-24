package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.TravelRequest;
import com.qaid.hrms.model.dto.response.TravelResponse;
import com.qaid.hrms.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/travels")
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PostMapping
    public ResponseEntity<TravelResponse> createTravel(@RequestBody TravelRequest request) {
        return ResponseEntity.ok(travelService.createTravel(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/{employeeId}")
    public ResponseEntity<TravelResponse> updateTravelByEmployeeId(@PathVariable String employeeId, @RequestBody TravelRequest request) {
        return ResponseEntity.ok(travelService.updateTravelByEmployeeId(employeeId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteTravelByEmployeeId(@PathVariable String employeeId) {
        travelService.deleteTravelByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/{employeeId}")
    public ResponseEntity<TravelResponse> getTravelByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(travelService.getTravelByEmployeeId(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<TravelResponse>> getAllTravels() {
        return ResponseEntity.ok(travelService.getAllTravels());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TravelResponse>> getTravelsByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(travelService.getTravelsByEmployee(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/date-range")
    public ResponseEntity<List<TravelResponse>> getTravelsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(travelService.getTravelsByDateRange(startDate, endDate));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/employee/{employeeId}/date-range")
    public ResponseEntity<List<TravelResponse>> getTravelsByEmployeeAndDateRange(
            @PathVariable String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(travelService.getTravelsByEmployeeAndDateRange(employeeId, startDate, endDate));
    }

    @PostMapping("/employee/{employeeId}/approve")
    public ResponseEntity<TravelResponse> approveTravelByEmployeeId(@PathVariable String employeeId, @RequestParam String comments) {
        return ResponseEntity.ok(travelService.approveTravelByEmployeeId(employeeId, comments));
    }

    @PostMapping("/employee/{employeeId}/reject")
    public ResponseEntity<TravelResponse> rejectTravelByEmployeeId(@PathVariable String employeeId, @RequestParam String comments) {
        return ResponseEntity.ok(travelService.rejectTravelByEmployeeId(employeeId, comments));
    }

    @PutMapping("/employee/{employeeId}/actual-cost")
    public ResponseEntity<TravelResponse> updateActualCostByEmployeeId(@PathVariable String employeeId, @RequestParam BigDecimal actualCost, @RequestParam String comments) {
        return ResponseEntity.ok(travelService.updateActualCostByEmployeeId(employeeId, actualCost, comments));
    }
}
