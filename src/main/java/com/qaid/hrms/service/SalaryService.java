package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.SalaryRequest;
import com.qaid.hrms.model.dto.response.SalaryResponse;

import java.time.LocalDate;
import java.util.List;

public interface SalaryService {
    SalaryResponse createSalary(SalaryRequest salaryRequest);
    SalaryResponse updateSalary(Long id, SalaryRequest salaryRequest);
    void deleteSalary(Long id);
    SalaryResponse getSalaryById(Long id);
    List<SalaryResponse> getAllSalaries();
    List<SalaryResponse> getSalariesByEmployee(Long employeeId);
    List<SalaryResponse> getSalariesByPayPeriod(LocalDate startDate, LocalDate endDate);
    List<SalaryResponse> getSalariesByEmployeeAndPayPeriod(Long employeeId, LocalDate startDate, LocalDate endDate);
    SalaryResponse approveSalary(Long id, String comments);
    SalaryResponse rejectSalary(Long id, String comments);
} 