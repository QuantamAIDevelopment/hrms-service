package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.EmployeeRequest;
import com.qaid.hrms.model.dto.response.EmployeeResponse;
import com.qaid.hrms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.qaid.hrms.util.ValidationUtils;
import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest request) {
        ValidationUtils.validateRequired(request.getFirstName(), "First Name");
        ValidationUtils.validateRequired(request.getLastName(), "Last Name");
        ValidationUtils.validateEmail(request.getWorkEmail());
        return ResponseEntity.ok(employeeService.createEmployee(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable String employeeId, @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.updateEmployeeByEmployeeId(employeeId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) {
        employeeService.deleteEmployeeByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> getEmployeeByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeByEmployeeId(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByDepartment(@PathVariable String departmentId) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(departmentId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/supervisor/{supervisorEmployeeId}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesBySupervisor(@PathVariable String supervisorEmployeeId) {
        return ResponseEntity.ok(employeeService.getEmployeesBySupervisor(supervisorEmployeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @GetMapping("/exists/{employeeId}")
    public ResponseEntity<Boolean> existsByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.existsByEmployeeId(employeeId));
    }
}
