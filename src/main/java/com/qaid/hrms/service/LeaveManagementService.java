package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.LeaveRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface LeaveManagementService {
    // Leave Request Operations
    LeaveRequestDTO createLeaveRequest(LeaveRequestDTO leaveRequestDTO);
    LeaveRequestDTO updateLeaveRequest(Long id, LeaveRequestDTO leaveRequestDTO);
    LeaveRequestDTO getLeaveRequest(Long id);
    Page<LeaveRequestDTO> getEmployeeLeaveRequests(Long employeeId, Pageable pageable);
    Page<LeaveRequestDTO> getDepartmentLeaveRequests(Long departmentId, com.qaid.hrms.model.enums.LeaveStatus status, Pageable pageable);
    
    // Leave Approval Operations
    LeaveRequestDTO approveLeaveRequest(Long id, Long approverId);
    LeaveRequestDTO rejectLeaveRequest(Long id, Long approverId, String reason);
    LeaveRequestDTO cancelLeaveRequest(Long id);
    
    // Leave Balance Operations
    List<LeaveRequestDTO> getEmployeeLeaveBalance(Long employeeId, Integer year);
    boolean checkLeaveAvailability(Long employeeId, Long leaveTypeId, LocalDate startDate, LocalDate endDate);
    
    // Leave Type Operations
    List<LeaveRequestDTO> getAvailableLeaveTypes(Long employeeId);
    
    // Leave Calendar Operations
    List<LeaveRequestDTO> getDepartmentLeaveCalendar(Long departmentId, LocalDate startDate, LocalDate endDate);
    
    // Leave Statistics
    Object getLeaveStatistics(Long departmentId, Integer year);
    Object getEmployeeLeaveStatistics(Long employeeId, Integer year);
} 