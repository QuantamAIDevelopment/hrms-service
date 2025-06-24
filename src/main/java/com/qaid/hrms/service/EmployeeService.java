package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.EmployeeRequest;
import com.qaid.hrms.model.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest employeeRequest);
    EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest);
    void deleteEmployee(Long id);
    EmployeeResponse getEmployeeById(Long id);
    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse getEmployeeByEmployeeId(String employeeId);
    List<EmployeeResponse> getEmployeesByDepartment(Long departmentId);
    List<EmployeeResponse> getEmployeesBySupervisor(Long supervisorId);
    boolean existsByEmployeeId(String employeeId);
} 