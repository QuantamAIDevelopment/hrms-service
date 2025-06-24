package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.AttendanceRequest;
import com.qaid.hrms.model.dto.response.AttendanceResponse;
import com.qaid.hrms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<AttendanceResponse> createAttendance(@RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.createAttendance(request));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<AttendanceResponse> updateAttendanceByEmployeeId(@PathVariable String employeeId, @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.updateAttendanceByEmployeeId(employeeId, request));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteAttendanceByEmployeeId(@PathVariable String employeeId) {
        attendanceService.deleteAttendanceByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<AttendanceResponse> getAttendanceByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByEmployeeId(employeeId));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceResponse>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByEmployee(employeeId));
    }

    @GetMapping("/date")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByDate(date));
    }

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
