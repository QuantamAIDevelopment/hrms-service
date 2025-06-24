package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.EmployeeRequest;
import com.qaid.hrms.model.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest employeeRequest);
    EmployeeResponse updateEmployeeByEmployeeId(String employeeId, EmployeeRequest employeeRequest);
    void deleteEmployeeByEmployeeId(String employeeId);
    EmployeeResponse getEmployeeByEmployeeId(String employeeId);
    EmployeeResponse getEmployeeByEmail(String email);
    List<EmployeeResponse> getAllEmployees();
    List<EmployeeResponse> getEmployeesByDepartment(String departmentId);
    List<EmployeeResponse> getEmployeesBySupervisor(String supervisorEmployeeId);
    boolean existsByEmployeeId(String employeeId);
}