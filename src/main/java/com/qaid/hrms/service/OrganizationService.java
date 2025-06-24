package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.DepartmentRequestDTO;
import com.qaid.hrms.model.dto.response.DepartmentResponseDTO;
import com.qaid.hrms.model.dto.request.DesignationRequestDTO;
import com.qaid.hrms.model.dto.response.DesignationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrganizationService {
    // Department Operations
    DepartmentResponseDTO createDepartment(DepartmentRequestDTO departmentRequestDTO);
    DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO departmentRequestDTO);
    DepartmentResponseDTO getDepartment(Long id);
    Page<DepartmentResponseDTO> getAllDepartments(Pageable pageable);
    List<DepartmentResponseDTO> getDepartmentHierarchy();
    List<DepartmentResponseDTO> getSubDepartments(Long departmentId);
    
    // Department Management
    DepartmentResponseDTO assignManager(Long departmentId, Long employeeId);
    DepartmentResponseDTO updateDepartmentLocation(Long departmentId, String location);
    void deactivateDepartment(Long departmentId);
    
    // Designation Operations
    DesignationResponseDTO createDesignation(DesignationRequestDTO designationRequestDTO);
    DesignationResponseDTO updateDesignation(Long id, DesignationRequestDTO designationRequestDTO);
    DesignationResponseDTO getDesignation(Long id);
    List<DesignationResponseDTO> getDepartmentDesignations(Long departmentId);
    
    // Organization Structure
    Object getOrganizationChart();
    Object getDepartmentStructure(Long departmentId);
    
    // Statistics and Reports
    Object getDepartmentStatistics(Long departmentId);
    Object getOrganizationStatistics();
    
    // Employee Distribution
    Object getEmployeeDistributionByDepartment();
    Object getEmployeeDistributionByDesignation();
}