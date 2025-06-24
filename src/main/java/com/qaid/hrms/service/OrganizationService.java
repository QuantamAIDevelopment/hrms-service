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
    DepartmentResponseDTO updateDepartmentByAllocatedId(String departmentId, DepartmentRequestDTO departmentRequestDTO);
    DepartmentResponseDTO getDepartmentByAllocatedId(String departmentId);
    Page<DepartmentResponseDTO> getAllDepartments(Pageable pageable);
    List<DepartmentResponseDTO> getDepartmentHierarchy();
    List<DepartmentResponseDTO> getSubDepartments(String departmentId);
    
    // Department Management
    DepartmentResponseDTO assignManager(String departmentId, String employeeId);
    DepartmentResponseDTO updateDepartmentLocation(String departmentId, String location);
    void deactivateDepartment(String departmentId);
    
    // Designation Operations
    DesignationResponseDTO createDesignation(DesignationRequestDTO designationRequestDTO);
    DesignationResponseDTO updateDesignationByAllocatedId(String designationId, DesignationRequestDTO designationRequestDTO);
    DesignationResponseDTO getDesignationByAllocatedId(String designationId);
    List<DesignationResponseDTO> getDepartmentDesignations(String departmentId);
    
    // Organization Structure
    Object getOrganizationChart();
    Object getDepartmentStructure(String departmentId);
    
    // Statistics and Reports
    Object getDepartmentStatistics(String departmentId);
    Object getOrganizationStatistics();
    
    // Employee Distribution
    Object getEmployeeDistributionByDepartment();
    Object getEmployeeDistributionByDesignation();
}