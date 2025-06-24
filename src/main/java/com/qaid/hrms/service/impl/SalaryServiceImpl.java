package com.qaid.hrms.service.impl;

import com.qaid.hrms.exception.BadRequestException;
import com.qaid.hrms.exception.ResourceNotFoundException;
import com.qaid.hrms.model.dto.request.SalaryRequest;
import com.qaid.hrms.model.dto.response.SalaryResponse;
import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.model.entity.Salary;
import com.qaid.hrms.repository.EmployeeRepository;
import com.qaid.hrms.repository.SalaryRepository;
import com.qaid.hrms.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public SalaryResponse createSalary(SalaryRequest request) {
        System.out.println("===Salary Request==="+request);
        System.out.println("Finding Employee: --> "+employeeRepository.findById(request.getEmployeeId()));
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Check if salary already exists for the pay period
        if (salaryRepository.existsByEmployeeIdAndPayPeriodStartAndPayPeriodEnd(
                request.getEmployeeId(), request.getPayPeriodStart(), request.getPayPeriodEnd())) {
            throw new BadRequestException("Salary record already exists for this pay period");
        }

        Salary salary = getSalary(request, employee);

        return mapToResponse(salaryRepository.save(salary));
    }

    private static Salary getSalary(SalaryRequest request, Employee employee) {
        System.out.println("get the existing salary-->>>");
        Salary salary = new Salary();
        salary.setEmployee(employee);
        salary.setPayPeriodStart(request.getPayPeriodStart());
        salary.setPayPeriodEnd(request.getPayPeriodEnd());
        salary.setBasicSalary(request.getBasicSalary());
        salary.setAllowances(request.getAllowances() != null ? request.getAllowances() : BigDecimal.ZERO);
        salary.setDeductions(request.getDeductions() != null ? request.getDeductions() : BigDecimal.ZERO);
        salary.setPaymentDate(request.getPaymentDate());
        salary.setComments(request.getComments());
        salary.setStatus("Pending");

        // Calculate net salary
        BigDecimal netSalary = salary.getBasicSalary()
                .add(salary.getAllowances())
                .subtract(salary.getDeductions());
        salary.setNetSalary(netSalary);
        return salary;
    }

    @Override
    public List<SalaryResponse> getAllSalaries() {
        return salaryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SalaryResponse updateSalaryByEmployeeId(String employeeId, SalaryRequest request) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Salary> salaries = salaryRepository.findByEmployeeId(employee.getId());
        if (salaries.isEmpty()) {
            throw new ResourceNotFoundException("No salary records found for employee");
        }
        Salary salary = salaries.get(0); // For demo, update the first salary
        if (request.getPayPeriodStart() != null) {
            salary.setPayPeriodStart(request.getPayPeriodStart());
        }
        if (request.getPayPeriodEnd() != null) {
            salary.setPayPeriodEnd(request.getPayPeriodEnd());
        }
        if (request.getBasicSalary() != null) {
            salary.setBasicSalary(request.getBasicSalary());
        }
        if (request.getAllowances() != null) {
            salary.setAllowances(request.getAllowances());
        }
        if (request.getDeductions() != null) {
            salary.setDeductions(request.getDeductions());
        }
        if (request.getPaymentDate() != null) {
            salary.setPaymentDate(request.getPaymentDate());
        }
        if (request.getComments() != null) {
            salary.setComments(request.getComments());
        }
        // Recalculate net salary
        salary.setNetSalary(salary.getBasicSalary().add(salary.getAllowances()).subtract(salary.getDeductions()));
        return mapToResponse(salaryRepository.save(salary));
    }

    @Override
    public void deleteSalaryByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Salary> salaries = salaryRepository.findByEmployeeId(employee.getId());
        for (Salary salary : salaries) {
            if (!salary.getStatus().equals("Pending")) {
                throw new BadRequestException("Cannot delete approved or rejected salary record");
            }
            salaryRepository.deleteById(salary.getId());
        }
    }

    @Override
    public SalaryResponse getSalaryByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Salary> salaries = salaryRepository.findByEmployeeId(employee.getId());
        if (salaries.isEmpty()) {
            throw new ResourceNotFoundException("No salary records found for employee");
        }
        return mapToResponse(salaries.get(0)); // For demo, return the first salary
    }

    @Override
    public List<SalaryResponse> getSalariesByEmployee(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return salaryRepository.findByEmployeeId(employee.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalaryResponse> getSalariesByPayPeriod(LocalDate startDate, LocalDate endDate) {
        return salaryRepository.findByPayPeriodStartGreaterThanEqualAndPayPeriodEndLessThanEqual(startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalaryResponse> getSalariesByEmployeeAndPayPeriod(String employeeId, LocalDate startDate, LocalDate endDate) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return salaryRepository.findByEmployeeIdAndPayPeriodStartGreaterThanEqualAndPayPeriodEndLessThanEqual(
                employee.getId(), startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SalaryResponse approveSalaryByEmployeeId(String employeeId, String comments) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Salary> salaries = salaryRepository.findByEmployeeId(employee.getId());
        if (salaries.isEmpty()) {
            throw new ResourceNotFoundException("No salary records found for employee");
        }
        Salary salary = salaries.get(0); // For demo, approve the first salary
        if (!salary.getStatus().equals("Pending")) {
            throw new BadRequestException("Salary record is not in pending status");
        }
        salary.setStatus("Approved");
        salary.setComments(comments);
        return mapToResponse(salaryRepository.save(salary));
    }

    @Override
    public SalaryResponse rejectSalaryByEmployeeId(String employeeId, String comments) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Salary> salaries = salaryRepository.findByEmployeeId(employee.getId());
        if (salaries.isEmpty()) {
            throw new ResourceNotFoundException("No salary records found for employee");
        }
        Salary salary = salaries.get(0); // For demo, reject the first salary
        if (!salary.getStatus().equals("Pending")) {
            throw new BadRequestException("Salary record is not in pending status");
        }
        salary.setStatus("Rejected");
        salary.setComments(comments);
        return mapToResponse(salaryRepository.save(salary));
    }

    private SalaryResponse mapToResponse(Salary salary) {
        SalaryResponse response = new SalaryResponse();
        response.setId(salary.getId());
        response.setEmployeeId(salary.getEmployee().getEmployeeId());
        response.setEmployeeName(salary.getEmployee().getFirstName() + " " + salary.getEmployee().getLastName());
        response.setPayPeriodStart(salary.getPayPeriodStart());
        response.setPayPeriodEnd(salary.getPayPeriodEnd());
        response.setBasicSalary(salary.getBasicSalary());
        response.setAllowances(salary.getAllowances());
        response.setDeductions(salary.getDeductions());
        response.setNetSalary(salary.getNetSalary());
        response.setPaymentDate(salary.getPaymentDate());
        response.setStatus(salary.getStatus());
        response.setComments(salary.getComments());
        response.setCreatedAt(salary.getCreatedAt());
        response.setUpdatedAt(salary.getUpdatedAt());
        return response;
    }
}