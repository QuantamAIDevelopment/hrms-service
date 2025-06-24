package com.qaid.hrms.service.impl;

import com.qaid.hrms.exception.BadRequestException;
import com.qaid.hrms.exception.ResourceNotFoundException;
import com.qaid.hrms.model.dto.request.LeaveRequestRequestDTO;
import com.qaid.hrms.model.dto.response.LeaveRequestResponseDTO;
import com.qaid.hrms.model.entity.*;
import com.qaid.hrms.model.enums.LeaveStatus;
import com.qaid.hrms.repository.*;
import com.qaid.hrms.service.LeaveManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveManagementServiceImpl implements LeaveManagementService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;
    
    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;

    // --- Interface Methods Implementation ---
    @Override
    public LeaveRequestResponseDTO createLeaveRequest(LeaveRequestRequestDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
            
        LeaveType leaveType = leaveTypeRepository.findById(dto.getLeaveTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));
            
        // Validate leave dates
        validateLeaveDates(dto.getStartDate(), dto.getEndDate());
        
        // Check leave balance
        if (!checkLeaveAvailability(String.valueOf(employee.getId()), String.valueOf(leaveType.getId()), dto.getStartDate(), dto.getEndDate())) {
            throw new BadRequestException("Insufficient leave balance");
        }
        
        // Calculate total days
        int totalDays = calculateTotalDays(dto.getStartDate(), dto.getEndDate());
        
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setTotalDays(totalDays);
        leaveRequest.setReason(dto.getReason());
        leaveRequest.setStatus(LeaveStatus.PENDING);
        leaveRequest.setCreatedAt(LocalDate.now());
        leaveRequest.setUpdatedAt(LocalDate.now());
        
        leaveRequest = leaveRequestRepository.save(leaveRequest);
        return convertToResponse(leaveRequest);
    }
    @Override
    public LeaveRequestResponseDTO updateLeaveRequestByEmployeeId(String leaveRequestId, LeaveRequestRequestDTO dto) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(Long.parseLong(leaveRequestId))
            .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new BadRequestException("Cannot update leave request that is not in PENDING status");
        }
        validateLeaveDates(dto.getStartDate(), dto.getEndDate());
        String employeeId = String.valueOf(leaveRequest.getEmployee().getId());
        String leaveTypeId = String.valueOf(leaveRequest.getLeaveType().getId());
        // Fix checkLeaveAvailability usage in updateLeaveRequestByEmployeeId
        if (!leaveRequest.getStartDate().equals(dto.getStartDate()) ||
            !leaveRequest.getEndDate().equals(dto.getEndDate()) ||
            !leaveTypeId.equals(dto.getLeaveTypeId())) {
            if (!checkLeaveAvailability(employeeId, String.valueOf(dto.getLeaveTypeId()), dto.getStartDate(), dto.getEndDate())) {
                throw new BadRequestException("Insufficient leave balance");
            }
        }
        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setTotalDays(calculateTotalDays(dto.getStartDate(), dto.getEndDate()));
        leaveRequest.setReason(dto.getReason());
        leaveRequest.setUpdatedAt(LocalDate.now());
        // Fix leaveTypeId parsing for newLeaveType
        if (!leaveTypeId.equals(dto.getLeaveTypeId())) {
            LeaveType newLeaveType = leaveTypeRepository.findById(Long.parseLong(dto.getLeaveTypeId().toString()))
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));
            leaveRequest.setLeaveType(newLeaveType);
        }
        leaveRequest = leaveRequestRepository.save(leaveRequest);
        return convertToResponse(leaveRequest);
    }
    @Override
    public LeaveRequestResponseDTO getLeaveRequestByEmployeeId(String leaveRequestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(Long.parseLong(leaveRequestId))
            .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        return convertToResponse(leaveRequest);
    }
    @Override
    public org.springframework.data.domain.Page<LeaveRequestResponseDTO> getEmployeeLeaveRequests(String employeeId, Pageable pageable) {
        Employee employee = employeeRepository.findById(Long.parseLong(employeeId))
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return leaveRequestRepository.findByEmployee(employee, pageable)
            .map(this::convertToResponse);
    }
    @Override
    public org.springframework.data.domain.Page<LeaveRequestResponseDTO> getDepartmentLeaveRequests(String departmentId, com.qaid.hrms.model.enums.LeaveStatus status, Pageable pageable) {
        Department department = departmentRepository.findById(Long.parseLong(departmentId))
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return leaveRequestRepository.findByEmployeeDepartmentAndStatus(department, status, pageable)
            .map(this::convertToResponse);
    }
    @Override
    public LeaveRequestResponseDTO approveLeaveRequestByEmployeeId(String leaveRequestId, String approverEmployeeId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(Long.parseLong(leaveRequestId))
            .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        Employee approver = employeeRepository.findById(Long.parseLong(approverEmployeeId))
            .orElseThrow(() -> new ResourceNotFoundException("Approver not found"));
        validateApproverPermissions(leaveRequest, approver);
        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new BadRequestException("Cannot approve leave request that is not in PENDING status");
        }
        leaveRequest.setStatus(LeaveStatus.APPROVED);
        leaveRequest.setApprovedBy(approver);
        leaveRequest.setUpdatedAt(LocalDate.now());
        updateLeaveBalance(leaveRequest);
        leaveRequest = leaveRequestRepository.save(leaveRequest);
        return convertToResponse(leaveRequest);
    }
    @Override
    public LeaveRequestResponseDTO rejectLeaveRequestByEmployeeId(String leaveRequestId, String approverEmployeeId, String reason) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(Long.parseLong(leaveRequestId))
            .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        Employee approver = employeeRepository.findById(Long.parseLong(approverEmployeeId))
            .orElseThrow(() -> new ResourceNotFoundException("Approver not found"));
        validateApproverPermissions(leaveRequest, approver);
        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new BadRequestException("Cannot reject leave request that is not in PENDING status");
        }
        leaveRequest.setStatus(LeaveStatus.REJECTED);
        leaveRequest.setApprovedBy(approver);
        leaveRequest.setRejectionReason(reason);
        leaveRequest.setUpdatedAt(LocalDate.now());
        leaveRequest = leaveRequestRepository.save(leaveRequest);
        return convertToResponse(leaveRequest);
    }
    @Override
    public LeaveRequestResponseDTO cancelLeaveRequestByEmployeeId(String leaveRequestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(Long.parseLong(leaveRequestId))
            .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        if (leaveRequest.getStatus() != LeaveStatus.PENDING && 
            leaveRequest.getStatus() != LeaveStatus.APPROVED) {
            throw new BadRequestException("Cannot cancel leave request in current status");
        }
        if (leaveRequest.getStatus() == LeaveStatus.APPROVED) {
            LeaveBalance balance = leaveBalanceRepository
                .findByEmployeeAndLeaveTypeAndYear(
                    leaveRequest.getEmployee(),
                    leaveRequest.getLeaveType(),
                    LocalDate.now().getYear()
                )
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));
            balance.setUsedDays(balance.getUsedDays() - leaveRequest.getTotalDays());
            balance.calculateRemainingDays();
            leaveBalanceRepository.save(balance);
        }
        leaveRequest.setStatus(LeaveStatus.CANCELLED);
        leaveRequest.setUpdatedAt(LocalDate.now());
        leaveRequest = leaveRequestRepository.save(leaveRequest);
        return convertToResponse(leaveRequest);
    }
    @Override
    public boolean checkLeaveAvailability(String employeeId, String leaveTypeId, LocalDate startDate, LocalDate endDate) {
        Employee employee = employeeRepository.findById(Long.parseLong(employeeId))
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        LeaveType leaveType = leaveTypeRepository.findById(Long.parseLong(leaveTypeId))
            .orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));
        int requestedDays = calculateTotalDays(startDate, endDate);
        LeaveBalance balance = leaveBalanceRepository.findByEmployeeAndLeaveTypeAndYear(
            employee, leaveType, LocalDate.now().getYear()
        ).orElse(null);
        return balance != null && balance.getRemainingDays() >= requestedDays;
    }
    @Override
    public List<LeaveRequestResponseDTO> getEmployeeLeaveBalance(String employeeId, Integer year) {
        Employee employee = employeeRepository.findById(Long.parseLong(employeeId))
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return leaveBalanceRepository.findByEmployeeAndYear(employee, year).stream()
            .map(balance -> {
                LeaveRequestResponseDTO dto = new LeaveRequestResponseDTO();
                dto.setLeaveTypeId(balance.getLeaveType().getId());
                dto.setLeaveTypeName(balance.getLeaveType().getName());
                dto.setTotalDays(balance.getTotalDays());
                dto.setUsedDays(balance.getUsedDays());
                dto.setRemainingDays(balance.getRemainingDays());
                return dto;
            })
            .collect(Collectors.toList());
    }
    @Override
    public List<LeaveRequestResponseDTO> getAvailableLeaveTypes(String employeeId) {
        Employee employee = employeeRepository.findById(Long.parseLong(employeeId))
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return leaveBalanceRepository.findByEmployeeAndYear(employee, LocalDate.now().getYear())
            .stream()
            .map(balance -> {
                LeaveRequestResponseDTO dto = new LeaveRequestResponseDTO();
                dto.setLeaveTypeId(balance.getLeaveType().getId());
                dto.setLeaveTypeName(balance.getLeaveType().getName());
                dto.setRemainingDays(balance.getRemainingDays());
                return dto;
            })
            .collect(Collectors.toList());
    }
    @Override
    public List<LeaveRequestResponseDTO> getDepartmentLeaveCalendar(String departmentId, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        Department department = departmentRepository.findById(Long.parseLong(departmentId))
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            
        return leaveRequestRepository.findByEmployeeDepartmentAndDateRange(
            department, startDate, endDate, LeaveStatus.APPROVED)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    @Override
    public Object getLeaveStatistics(String departmentId, Integer year) {
        Department department = departmentRepository.findById(Long.parseLong(departmentId))
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            
        Map<String, Object> stats = new HashMap<>();
        
        // Get leave requests by status
        stats.put("pending", leaveRequestRepository.countByEmployeeDepartmentAndStatus(department, LeaveStatus.PENDING));
        stats.put("approved", leaveRequestRepository.countByEmployeeDepartmentAndStatus(department, LeaveStatus.APPROVED));
        stats.put("rejected", leaveRequestRepository.countByEmployeeDepartmentAndStatus(department, LeaveStatus.REJECTED));
        stats.put("cancelled", leaveRequestRepository.countByEmployeeDepartmentAndStatus(department, LeaveStatus.CANCELLED));
        
        // Get leave distribution by type
        stats.put("leaveTypeDistribution", leaveRequestRepository.findLeaveDistributionByType(department, year));
        
        // Get monthly leave distribution
        stats.put("monthlyDistribution", leaveRequestRepository.findMonthlyLeaveDistribution(department, year));
        
        return stats;
    }
    @Override
    public Object getEmployeeLeaveStatistics(String employeeId, Integer year) {
        Employee employee = employeeRepository.findById(Long.parseLong(employeeId))
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
            
        Map<String, Object> stats = new HashMap<>();
        
        // Get leave requests by status
        stats.put("pending", leaveRequestRepository.countByEmployeeAndStatus(employee, LeaveStatus.PENDING));
        stats.put("approved", leaveRequestRepository.countByEmployeeAndStatus(employee, LeaveStatus.APPROVED));
        stats.put("rejected", leaveRequestRepository.countByEmployeeAndStatus(employee, LeaveStatus.REJECTED));
        stats.put("cancelled", leaveRequestRepository.countByEmployeeAndStatus(employee, LeaveStatus.CANCELLED));
        
        // Get leave balance by type
        stats.put("leaveBalance", leaveBalanceRepository.findByEmployeeAndYear(employee, year)
            .stream()
            .map(balance -> {
                Map<String, Object> bal = new HashMap<>();
                bal.put("leaveType", balance.getLeaveType().getName());
                bal.put("totalDays", balance.getTotalDays());
                bal.put("usedDays", balance.getUsedDays());
                bal.put("remainingDays", balance.getRemainingDays());
                return bal;
            })
            .collect(Collectors.toList()));
            
        // Get monthly leave distribution
        stats.put("monthlyDistribution", leaveRequestRepository.findMonthlyLeaveDistribution(employee, year));
        
        return stats;
    }

    // Helper methods
    private void validateLeaveDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new BadRequestException("Start date and end date are required");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date cannot be after end date");
        }
        
        if (startDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("Cannot apply for leave in the past");
        }
    }

    private int calculateTotalDays(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    private void validateApproverPermissions(LeaveRequest leaveRequest, Employee approver) {
        // Check if approver is in the same department or is a manager
        if (!approver.getDepartment().equals(leaveRequest.getEmployee().getDepartment()) &&
            !approver.getDesignation().getTitle().toLowerCase().contains("manager")) {
            throw new BadRequestException("Approver does not have permission to approve this leave request");
        }
    }

    private void updateLeaveBalance(LeaveRequest leaveRequest) {
        LeaveBalance balance = leaveBalanceRepository
            .findByEmployeeAndLeaveTypeAndYear(
                leaveRequest.getEmployee(),
                leaveRequest.getLeaveType(),
                LocalDate.now().getYear()
            )
            .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));
            
        balance.setUsedDays(balance.getUsedDays() + leaveRequest.getTotalDays());
        balance.calculateRemainingDays();
        leaveBalanceRepository.save(balance);
    }
    private LeaveRequestResponseDTO convertToResponse(LeaveRequest leaveRequest) {
        LeaveRequestResponseDTO dto = new LeaveRequestResponseDTO();
        dto.setId(leaveRequest.getId());
        dto.setEmployeeId(leaveRequest.getEmployee().getId());
        dto.setEmployeeName(leaveRequest.getEmployee().getFirstName() + " " + leaveRequest.getEmployee().getLastName());
        dto.setLeaveTypeId(leaveRequest.getLeaveType().getId());
        dto.setLeaveTypeName(leaveRequest.getLeaveType().getName());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setTotalDays(leaveRequest.getTotalDays());
        dto.setReason(leaveRequest.getReason());
        dto.setStatus(leaveRequest.getStatus().name());
        if (leaveRequest.getApprovedBy() != null) {
            dto.setApprovedById(leaveRequest.getApprovedBy().getId());
            dto.setApproverName(leaveRequest.getApprovedBy().getFirstName() + " " + leaveRequest.getApprovedBy().getLastName());
        }
        dto.setRejectionReason(leaveRequest.getRejectionReason());
        dto.setCreatedAt(leaveRequest.getCreatedAt());
        dto.setUpdatedAt(leaveRequest.getUpdatedAt());
        dto.setDepartmentName(leaveRequest.getEmployee().getDepartment().getName());
        return dto;
    }

    // Implement other interface methods...
}