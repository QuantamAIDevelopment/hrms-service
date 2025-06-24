package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.DepartmentDTO;
import com.qaid.hrms.model.dto.DesignationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrganizationService {
    // Department Operations
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO);
    DepartmentDTO getDepartment(Long id);
    Page<DepartmentDTO> getAllDepartments(Pageable pageable);
    List<DepartmentDTO> getDepartmentHierarchy();
    List<DepartmentDTO> getSubDepartments(Long departmentId);
    
    // Department Management
    DepartmentDTO assignManager(Long departmentId, Long employeeId);
    DepartmentDTO updateDepartmentLocation(Long departmentId, String location);
    void deactivateDepartment(Long departmentId);
    
    // Designation Operations
    DesignationDTO createDesignation(DesignationDTO designationDTO);
    DesignationDTO updateDesignation(Long id, DesignationDTO designationDTO);
    DesignationDTO getDesignation(Long id);
    List<DesignationDTO> getDepartmentDesignations(Long departmentId);
    
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