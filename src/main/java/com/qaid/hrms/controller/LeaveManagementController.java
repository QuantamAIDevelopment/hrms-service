package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.LeaveRequestRequestDTO;
import com.qaid.hrms.model.dto.response.LeaveRequestResponseDTO;
import com.qaid.hrms.service.LeaveManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/leaves")
@RequiredArgsConstructor
public class LeaveManagementController {
    private final LeaveManagementService leaveManagementService;

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @PostMapping
    public ResponseEntity<LeaveRequestResponseDTO> createLeaveRequest(@RequestBody LeaveRequestRequestDTO request) {
        return ResponseEntity.ok(leaveManagementService.createLeaveRequest(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @PutMapping("/{employeeId}")
    public ResponseEntity<LeaveRequestResponseDTO> updateLeaveRequestByEmployeeId(@PathVariable String employeeId, @RequestBody LeaveRequestRequestDTO request) {
        return ResponseEntity.ok(leaveManagementService.updateLeaveRequestByEmployeeId(employeeId, request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/{employeeId}")
    public ResponseEntity<LeaveRequestResponseDTO> getLeaveRequestByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(leaveManagementService.getLeaveRequestByEmployeeId(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Page<LeaveRequestResponseDTO>> getEmployeeLeaveRequests(@PathVariable String employeeId, Pageable pageable) {
        return ResponseEntity.ok(leaveManagementService.getEmployeeLeaveRequests(employeeId, pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<Page<LeaveRequestResponseDTO>> getDepartmentLeaveRequests(@PathVariable String departmentId, @RequestParam String status, Pageable pageable) {
        return ResponseEntity.ok(leaveManagementService.getDepartmentLeaveRequests(departmentId, com.qaid.hrms.model.enums.LeaveStatus.valueOf(status), pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @PostMapping("/{employeeId}/approve")
    public ResponseEntity<LeaveRequestResponseDTO> approveLeaveRequestByEmployeeId(@PathVariable String employeeId, @RequestParam String approverEmployeeId) {
        return ResponseEntity.ok(leaveManagementService.approveLeaveRequestByEmployeeId(employeeId, approverEmployeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @PostMapping("/{employeeId}/reject")
    public ResponseEntity<LeaveRequestResponseDTO> rejectLeaveRequestByEmployeeId(@PathVariable String employeeId, @RequestParam String approverEmployeeId, @RequestParam String reason) {
        return ResponseEntity.ok(leaveManagementService.rejectLeaveRequestByEmployeeId(employeeId, approverEmployeeId, reason));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @PostMapping("/{employeeId}/cancel")
    public ResponseEntity<LeaveRequestResponseDTO> cancelLeaveRequestByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(leaveManagementService.cancelLeaveRequestByEmployeeId(employeeId));
    }

    @GetMapping("/employee/{employeeId}/balance")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getEmployeeLeaveBalance(@PathVariable String employeeId, @RequestParam Integer year) {
        return ResponseEntity.ok(leaveManagementService.getEmployeeLeaveBalance(employeeId, year));
    }

    @GetMapping("/check-availability")
    public ResponseEntity<Boolean> checkLeaveAvailability(@RequestParam String employeeId, @RequestParam String leaveTypeId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(leaveManagementService.checkLeaveAvailability(employeeId, leaveTypeId, startDate, endDate));
    }

    @GetMapping("/employee/{employeeId}/available-types")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getAvailableLeaveTypes(@PathVariable String employeeId) {
        return ResponseEntity.ok(leaveManagementService.getAvailableLeaveTypes(employeeId));
    }

    @GetMapping("/department/{departmentId}/calendar")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getDepartmentLeaveCalendar(@PathVariable String departmentId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(leaveManagementService.getDepartmentLeaveCalendar(departmentId, startDate, endDate));
    }

    @GetMapping("/department/{departmentId}/statistics")
    public ResponseEntity<Object> getLeaveStatistics(@PathVariable String departmentId, @RequestParam Integer year) {
        return ResponseEntity.ok(leaveManagementService.getLeaveStatistics(departmentId, year));
    }

    @GetMapping("/employee/{employeeId}/statistics")
    public ResponseEntity<Object> getEmployeeLeaveStatistics(@PathVariable String employeeId, @RequestParam Integer year) {
        return ResponseEntity.ok(leaveManagementService.getEmployeeLeaveStatistics(employeeId, year));
    }
}
