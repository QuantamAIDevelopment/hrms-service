package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.AttendanceRequest;
import com.qaid.hrms.model.dto.response.AttendanceResponse;
import com.qaid.hrms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PostMapping
    public ResponseEntity<AttendanceResponse> createAttendance(@RequestBody AttendanceRequest request) {
        if (request.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID is required");
        }
        return ResponseEntity.ok(attendanceService.createAttendance(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/{employeeId}")
    public ResponseEntity<AttendanceResponse> updateAttendanceByEmployeeId(@PathVariable String employeeId, @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.updateAttendanceByEmployeeId(employeeId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteAttendanceByEmployeeId(@PathVariable String employeeId) {
        attendanceService.deleteAttendanceByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/{employeeId}")
    public ResponseEntity<AttendanceResponse> getAttendanceByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByEmployeeId(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<AttendanceResponse>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByEmployee(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/date")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByDate(date));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/employee/{employeeId}/date")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByEmployeeAndDate(@PathVariable String employeeId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByEmployeeAndDate(employeeId, date));
    }

    @PostMapping("/employee/{employeeId}/check-in")
    public ResponseEntity<AttendanceResponse> checkIn(@PathVariable String employeeId) {
        return ResponseEntity.ok(attendanceService.checkIn(employeeId));
    }

    @PostMapping("/employee/{employeeId}/check-out")
    public ResponseEntity<AttendanceResponse> checkOut(@PathVariable String employeeId) {
        return ResponseEntity.ok(attendanceService.checkOut(employeeId));
    }
}
