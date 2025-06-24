package com.qaid.hrms.service.impl;

import com.qaid.hrms.exception.BadRequestException;
import com.qaid.hrms.exception.ResourceNotFoundException;
import com.qaid.hrms.model.dto.request.EmployeeRequest;
import com.qaid.hrms.model.dto.response.EmployeeResponse;
import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.repository.*;
import com.qaid.hrms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private EmploymentStatusRepository employmentStatusRepository;
    @Autowired
    private JobTitleRepository jobTitleRepository;
    @Autowired
    private PayGradeRepository payGradeRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        if (existsByEmployeeId(request.getEmployeeId())) {
            throw new BadRequestException("Employee ID is already taken");
        }

        Employee employee = new Employee();
        mapRequestToEntity(request, employee);
        employee.setStatus("Active");

        return mapToResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployeeByEmployeeId(String employeeId, EmployeeRequest request) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with employee ID: " + employeeId));
        if (!employee.getEmployeeId().equals(request.getEmployeeId()) && existsByEmployeeId(request.getEmployeeId())) {
            throw new BadRequestException("Employee ID is already taken");
        }
        mapRequestToEntity(request, employee);
        return mapToResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public void deleteEmployeeByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with employee ID: " + employeeId));
        employeeRepository.deleteById(employee.getId());
    }

    @Override
    public EmployeeResponse getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByWorkEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email: " + email));
        return mapToResponse(employee);
    }

    @Override
    public EmployeeResponse getEmployeeByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with employee ID: " + employeeId));
        return mapToResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmployeeId(String employeeId) {

        return employeeRepository.existsByEmployeeId(employeeId);
    }

    @Override
    public List<EmployeeResponse> getEmployeesByDepartment(String departmentId) {
        return employeeRepository.findByDepartmentId(Long.parseLong(departmentId)).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponse> getEmployeesBySupervisor(String supervisorEmployeeId) {
        Employee supervisor = employeeRepository.findByEmployeeId(supervisorEmployeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Supervisor not found with employee ID: " + supervisorEmployeeId));
        return employeeRepository.findBySupervisorId(supervisor.getId()).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private void mapRequestToEntity(EmployeeRequest request, Employee employee) {

        employee.setEmployeeId(request.getEmployeeId());
        employee.setFirstName(request.getFirstName());
        employee.setMiddleName(request.getMiddleName());
        employee.setLastName(request.getLastName());
        
        if (request.getNationality() != null) {
            employee.setNationality(countryRepository.findById(request.getNationality())
                    .orElseThrow(() -> new ResourceNotFoundException("Country not found")));
        }
        
        employee.setBirthday(request.getBirthday());
        employee.setGender(request.getGender());
        employee.setMaritalStatus(request.getMaritalStatus());
        employee.setSsnNum(request.getSsnNum());
        employee.setNicNum(request.getNicNum());
        employee.setOtherId(request.getOtherId());

        if (request.getEmploymentStatus() != null) {
            employee.setEmploymentStatus(employmentStatusRepository.findById(request.getEmploymentStatus())
                    .orElseThrow(() -> new ResourceNotFoundException("Employment status not found")));
        }

        if (request.getJobTitle() != null) {
            employee.setJobTitle(jobTitleRepository.findById(request.getJobTitle())
                    .orElseThrow(() -> new ResourceNotFoundException("Job title not found")));
        }

        if (request.getPayGrade() != null) {
            employee.setPayGrade(payGradeRepository.findById(request.getPayGrade())
                    .orElseThrow(() -> new ResourceNotFoundException("Pay grade not found")));
        }

        employee.setWorkStationId(request.getWorkStationId());
        employee.setAddress1(request.getAddress1());
        employee.setAddress2(request.getAddress2());
        employee.setCity(request.getCity());
        
        if (request.getCountry() != null) {
            employee.setCountry(countryRepository.findByName(request.getCountry())
                    .orElseThrow(() -> new ResourceNotFoundException("Country not found")));
        }

        if (request.getProvince() != null) {
            employee.setProvince(provinceRepository.findById(request.getProvince())
                    .orElseThrow(() -> new ResourceNotFoundException("Province not found")));
        }

        employee.setPostalCode(request.getPostalCode());
        employee.setHomePhone(request.getHomePhone());
        employee.setMobilePhone(request.getMobilePhone());
        employee.setWorkPhone(request.getWorkPhone());
        employee.setWorkEmail(request.getWorkEmail());
        employee.setPrivateEmail(request.getPrivateEmail());
        employee.setJoinedDate(request.getJoinedDate());
        employee.setConfirmationDate(request.getConfirmationDate());

        if (request.getSupervisor() != null) {
            employee.setSupervisor(employeeRepository.findByEmployeeId(request.getSupervisor())
                    .orElseThrow(() -> new ResourceNotFoundException("Supervisor not found")));
        }

        employee.setIndirectSupervisors(request.getIndirectSupervisors());

        if (request.getDepartment() != null) {
            employee.setDepartment(departmentRepository.findById(request.getDepartment())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found")));
        }

        employee.setTimezone(request.getTimezone());
        employee.setProfileImage(request.getProfileImage());
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        System.out.println("===Employees Details Entering======");
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setEmployeeId(employee.getEmployeeId());
        response.setFirstName(employee.getFirstName());
        response.setMiddleName(employee.getMiddleName());
        response.setLastName(employee.getLastName());
        response.setNationality(employee.getNationality() != null ? employee.getNationality().getName() : null);
        response.setBirthday(employee.getBirthday());
        response.setGender(employee.getGender());
        response.setMaritalStatus(employee.getMaritalStatus());
        response.setSsnNum(employee.getSsnNum());
        response.setNicNum(employee.getNicNum());
        response.setOtherId(employee.getOtherId());
        response.setEmploymentStatus(employee.getEmploymentStatus() != null ? employee.getEmploymentStatus().getName() : null);
        response.setJobTitle(employee.getJobTitle() != null ? employee.getJobTitle().getName() : null);
        response.setPayGrade(employee.getPayGrade() != null ? employee.getPayGrade().getName() : null);
        response.setWorkStationId(employee.getWorkStationId());
        response.setAddress1(employee.getAddress1());
        response.setAddress2(employee.getAddress2());
        response.setCity(employee.getCity());
        response.setCountry(employee.getCountry() != null ? employee.getCountry().getName() : null);
        response.setProvince(employee.getProvince() != null ? employee.getProvince().getName() : null);
        response.setPostalCode(employee.getPostalCode());
        response.setHomePhone(employee.getHomePhone());
        response.setMobilePhone(employee.getMobilePhone());
        response.setWorkPhone(employee.getWorkPhone());
        response.setWorkEmail(employee.getWorkEmail());
        response.setPrivateEmail(employee.getPrivateEmail());
        response.setJoinedDate(employee.getJoinedDate());
        response.setConfirmationDate(employee.getConfirmationDate());
        response.setSupervisor(employee.getSupervisor() != null ? employee.getSupervisor().getEmployeeId() : null);
        response.setIndirectSupervisors(employee.getIndirectSupervisors());
        response.setDepartment(employee.getDepartment() != null ? employee.getDepartment().getName() : null);
        response.setStatus(employee.getStatus());
        response.setTimezone(employee.getTimezone());
        response.setProfileImage(employee.getProfileImage());
        response.setCreatedAt(employee.getCreatedAt());
        response.setUpdatedAt(employee.getUpdatedAt());
        System.out.println("===Employees Details Exiting======"+response);
        return response;
    }
}