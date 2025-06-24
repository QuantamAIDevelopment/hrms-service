package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.SalaryRequest;
import com.qaid.hrms.model.dto.response.SalaryResponse;

import java.time.LocalDate;
import java.util.List;

public interface SalaryService {
    SalaryResponse createSalary(SalaryRequest salaryRequest);
    SalaryResponse updateSalaryByEmployeeId(String employeeId, SalaryRequest salaryRequest);
    void deleteSalaryByEmployeeId(String employeeId);
    SalaryResponse getSalaryByEmployeeId(String employeeId);
    List<SalaryResponse> getAllSalaries();
    List<SalaryResponse> getSalariesByEmployee(String employeeId);
    List<SalaryResponse> getSalariesByPayPeriod(LocalDate startDate, LocalDate endDate);
    List<SalaryResponse> getSalariesByEmployeeAndPayPeriod(String employeeId, LocalDate startDate, LocalDate endDate);
    SalaryResponse approveSalaryByEmployeeId(String employeeId, String comments);
    SalaryResponse rejectSalaryByEmployeeId(String employeeId, String comments);
}