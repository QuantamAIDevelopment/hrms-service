package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.DepartmentRequestDTO;
import com.qaid.hrms.model.dto.response.DepartmentResponseDTO;
import com.qaid.hrms.model.dto.request.DesignationRequestDTO;
import com.qaid.hrms.model.dto.response.DesignationResponseDTO;
import com.qaid.hrms.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    // Department Operations
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PostMapping("/departments")
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody DepartmentRequestDTO request) {
        return ResponseEntity.ok(organizationService.createDepartment(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/departments/{departmentId}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable String departmentId, @RequestBody DepartmentRequestDTO request) {
        return ResponseEntity.ok(organizationService.updateDepartmentByAllocatedId(departmentId, request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/departments/{departmentId}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentByAllocatedId(@PathVariable String departmentId) {
        return ResponseEntity.ok(organizationService.getDepartmentByAllocatedId(departmentId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/departments")
    public ResponseEntity<Page<DepartmentResponseDTO>> getAllDepartments(Pageable pageable) {
        return ResponseEntity.ok(organizationService.getAllDepartments(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/departments/hierarchy")
    public ResponseEntity<List<DepartmentResponseDTO>> getDepartmentHierarchy() {
        return ResponseEntity.ok(organizationService.getDepartmentHierarchy());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/departments/{departmentId}/sub")
    public ResponseEntity<List<DepartmentResponseDTO>> getSubDepartments(@PathVariable String departmentId) {
        return ResponseEntity.ok(organizationService.getSubDepartments(departmentId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PostMapping("/departments/{departmentId}/assign-manager")
    public ResponseEntity<DepartmentResponseDTO> assignManager(@PathVariable String departmentId, @RequestParam String employeeId) {
        return ResponseEntity.ok(organizationService.assignManager(departmentId, employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/departments/{departmentId}/location")
    public ResponseEntity<DepartmentResponseDTO> updateDepartmentLocation(@PathVariable String departmentId, @RequestParam String location) {
        return ResponseEntity.ok(organizationService.updateDepartmentLocation(departmentId, location));
    }

    @DeleteMapping("/departments/{departmentId}")
    public ResponseEntity<Void> deactivateDepartment(@PathVariable String departmentId) {
        organizationService.deactivateDepartment(departmentId);
        return ResponseEntity.noContent().build();
    }

    // Designation Operations
    @PostMapping("/designations")
    public ResponseEntity<DesignationResponseDTO> createDesignation(@RequestBody DesignationRequestDTO request) {
        return ResponseEntity.ok(organizationService.createDesignation(request));
    }

    @PutMapping("/designations/{designationId}")
    public ResponseEntity<DesignationResponseDTO> updateDesignation(@PathVariable String designationId, @RequestBody DesignationRequestDTO request) {
        return ResponseEntity.ok(organizationService.updateDesignationByAllocatedId(designationId, request));
    }

    @GetMapping("/designations/{designationId}")
    public ResponseEntity<DesignationResponseDTO> getDesignationByAllocatedId(@PathVariable String designationId) {
        return ResponseEntity.ok(organizationService.getDesignationByAllocatedId(designationId));
    }

    @GetMapping("/departments/{departmentId}/designations")
    public ResponseEntity<List<DesignationResponseDTO>> getDepartmentDesignations(@PathVariable String departmentId) {
        return ResponseEntity.ok(organizationService.getDepartmentDesignations(departmentId));
    }

    // Organization Structure
    @GetMapping("/chart")
    public ResponseEntity<Object> getOrganizationChart() {
        return ResponseEntity.ok(organizationService.getOrganizationChart());
    }

    @GetMapping("/departments/{departmentId}/structure")
    public ResponseEntity<Object> getDepartmentStructure(@PathVariable String departmentId) {
        return ResponseEntity.ok(organizationService.getDepartmentStructure(departmentId));
    }

    // Statistics and Reports
    @GetMapping("/departments/{departmentId}/statistics")
    public ResponseEntity<Object> getDepartmentStatistics(@PathVariable String departmentId) {
        return ResponseEntity.ok(organizationService.getDepartmentStatistics(departmentId));
    }

    @GetMapping("/statistics")
    public ResponseEntity<Object> getOrganizationStatistics() {
        return ResponseEntity.ok(organizationService.getOrganizationStatistics());
    }

    // Employee Distribution
    @GetMapping("/distribution/department")
    public ResponseEntity<Object> getEmployeeDistributionByDepartment() {
        return ResponseEntity.ok(organizationService.getEmployeeDistributionByDepartment());
    }

    @GetMapping("/distribution/designation")
    public ResponseEntity<Object> getEmployeeDistributionByDesignation() {
        return ResponseEntity.ok(organizationService.getEmployeeDistributionByDesignation());
    }
}
