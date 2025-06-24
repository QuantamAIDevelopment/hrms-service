package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.LeaveRequestRequestDTO;
import com.qaid.hrms.model.dto.response.LeaveRequestResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface LeaveManagementService {
    // Leave Request Operations
    LeaveRequestResponseDTO createLeaveRequest(LeaveRequestRequestDTO leaveRequestRequestDTO);
    LeaveRequestResponseDTO updateLeaveRequest(Long id, LeaveRequestRequestDTO leaveRequestRequestDTO);
    LeaveRequestResponseDTO getLeaveRequest(Long id);
    Page<LeaveRequestResponseDTO> getEmployeeLeaveRequests(Long employeeId, Pageable pageable);
    Page<LeaveRequestResponseDTO> getDepartmentLeaveRequests(Long departmentId, com.qaid.hrms.model.enums.LeaveStatus status, Pageable pageable);
    
    // Leave Approval Operations
    LeaveRequestResponseDTO approveLeaveRequest(Long id, Long approverId);
    LeaveRequestResponseDTO rejectLeaveRequest(Long id, Long approverId, String reason);
    LeaveRequestResponseDTO cancelLeaveRequest(Long id);
    
    // Leave Balance Operations
    List<LeaveRequestResponseDTO> getEmployeeLeaveBalance(Long employeeId, Integer year);
    boolean checkLeaveAvailability(Long employeeId, Long leaveTypeId, LocalDate startDate, LocalDate endDate);
    
    // Leave Type Operations
    List<LeaveRequestResponseDTO> getAvailableLeaveTypes(Long employeeId);
    
    // Leave Calendar Operations
    List<LeaveRequestResponseDTO> getDepartmentLeaveCalendar(Long departmentId, LocalDate startDate, LocalDate endDate);
    
    // Leave Statistics
    Object getLeaveStatistics(Long departmentId, Integer year);
    Object getEmployeeLeaveStatistics(Long employeeId, Integer year);
}