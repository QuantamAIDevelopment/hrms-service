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
    LeaveRequestResponseDTO updateLeaveRequestByEmployeeId(String employeeId, LeaveRequestRequestDTO leaveRequestRequestDTO);
    LeaveRequestResponseDTO getLeaveRequestByEmployeeId(String employeeId);
    Page<LeaveRequestResponseDTO> getEmployeeLeaveRequests(String employeeId, Pageable pageable);
    Page<LeaveRequestResponseDTO> getDepartmentLeaveRequests(String departmentId, com.qaid.hrms.model.enums.LeaveStatus status, Pageable pageable);
    
    // Leave Approval Operations
    LeaveRequestResponseDTO approveLeaveRequestByEmployeeId(String employeeId, String approverEmployeeId);
    LeaveRequestResponseDTO rejectLeaveRequestByEmployeeId(String employeeId, String approverEmployeeId, String reason);
    LeaveRequestResponseDTO cancelLeaveRequestByEmployeeId(String employeeId);
    
    // Leave Balance Operations
    List<LeaveRequestResponseDTO> getEmployeeLeaveBalance(String employeeId, Integer year);
    boolean checkLeaveAvailability(String employeeId, String leaveTypeId, LocalDate startDate, LocalDate endDate);
    
    // Leave Type Operations
    List<LeaveRequestResponseDTO> getAvailableLeaveTypes(String employeeId);
    
    // Leave Calendar Operations
    List<LeaveRequestResponseDTO> getDepartmentLeaveCalendar(String departmentId, LocalDate startDate, LocalDate endDate);
    
    // Leave Statistics
    Object getLeaveStatistics(String departmentId, Integer year);
    Object getEmployeeLeaveStatistics(String employeeId, Integer year);
}