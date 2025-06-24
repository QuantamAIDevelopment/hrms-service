package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.TimeSheetRequest;
import com.qaid.hrms.model.dto.response.TimeSheetResponse;
import com.qaid.hrms.service.TimeSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/timesheets")
@RequiredArgsConstructor
public class TimeSheetController {
    private final TimeSheetService timeSheetService;

    @PostMapping
    public ResponseEntity<TimeSheetResponse> createTimeSheet(@RequestBody TimeSheetRequest request) {
        return ResponseEntity.ok(timeSheetService.createTimeSheet(request));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<TimeSheetResponse> updateTimeSheetByEmployeeId(@PathVariable String employeeId, @RequestBody TimeSheetRequest request) {
        return ResponseEntity.ok(timeSheetService.updateTimeSheetByEmployeeId(employeeId, request));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteTimeSheetByEmployeeId(@PathVariable String employeeId) {
        timeSheetService.deleteTimeSheetByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<TimeSheetResponse> getTimeSheetByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(timeSheetService.getTimeSheetByEmployeeId(employeeId));
    }

    @GetMapping
    public ResponseEntity<List<TimeSheetResponse>> getAllTimeSheets() {
        return ResponseEntity.ok(timeSheetService.getAllTimeSheets());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TimeSheetResponse>> getTimeSheetsByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(timeSheetService.getTimeSheetsByEmployee(employeeId));
    }

    @GetMapping("/date")
    public ResponseEntity<List<TimeSheetResponse>> getTimeSheetsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(timeSheetService.getTimeSheetsByDate(date));
    }

    @GetMapping("/employee/{employeeId}/date")
    public ResponseEntity<List<TimeSheetResponse>> getTimeSheetsByEmployeeAndDate(
            @PathVariable String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(timeSheetService.getTimeSheetsByEmployeeAndDate(employeeId, date));
    }

    @PostMapping("/employee/{employeeId}/approve")
    public ResponseEntity<TimeSheetResponse> approveTimeSheetByEmployeeId(@PathVariable String employeeId, @RequestParam String comments) {
        return ResponseEntity.ok(timeSheetService.approveTimeSheetByEmployeeId(employeeId, comments));
    }

    @PostMapping("/employee/{employeeId}/reject")
    public ResponseEntity<TimeSheetResponse> rejectTimeSheetByEmployeeId(@PathVariable String employeeId, @RequestParam String comments) {
        return ResponseEntity.ok(timeSheetService.rejectTimeSheetByEmployeeId(employeeId, comments));
    }
}
