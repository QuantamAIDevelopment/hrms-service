package com.qaid.hrms.service.impl;

import com.qaid.hrms.model.dto.request.SelfServicePortalRequest;
import com.qaid.hrms.model.dto.response.SelfServicePortalResponse;
import com.qaid.hrms.service.SelfServicePortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.repository.EmployeeRepository;
import com.qaid.hrms.exception.ResourceNotFoundException;
import com.qaid.hrms.model.entity.SelfServicePortal;
import com.qaid.hrms.repository.SelfServicePortalRepository;

@Service
public class SelfServicePortalServiceImpl implements SelfServicePortalService {
    // In-memory store for demonstration
    private final List<SelfServicePortalResponse> portalList = new ArrayList<>();
    private long idCounter = 1;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SelfServicePortalRepository selfServicePortalRepository;

    @Override
    public SelfServicePortalResponse createSelfServicePortal(SelfServicePortalRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        SelfServicePortal portal = new SelfServicePortal();
        portal.setEmployee(employee);
        // ... set other fields ...
        return mapToResponse(selfServicePortalRepository.save(portal));
    }

    @Override
    public SelfServicePortalResponse updateSelfServicePortalByEmployeeId(String employeeId, SelfServicePortalRequest request) {
        Employee employee = employeeRepository.findById(Long.valueOf(employeeId)).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<SelfServicePortal> portals = selfServicePortalRepository.findByEmployee(employee);
        if (portals.isEmpty()) throw new ResourceNotFoundException("No portal found for employee");
        SelfServicePortal portal = portals.get(0);
        // ... update fields ...
        return mapToResponse(selfServicePortalRepository.save(portal));
    }

    @Override
    public void deleteSelfServicePortalByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findById(Long.valueOf(employeeId)).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<SelfServicePortal> portals = selfServicePortalRepository.findByEmployee(employee);
        if (portals.isEmpty()) throw new ResourceNotFoundException("No portal found for employee");
        selfServicePortalRepository.delete(portals.get(0));
    }

    @Override
    public SelfServicePortalResponse getSelfServicePortalByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findById(Long.valueOf(employeeId)).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<SelfServicePortal> portals = selfServicePortalRepository.findByEmployee(employee);
        if (portals.isEmpty()) throw new ResourceNotFoundException("No portal found for employee");
        return mapToResponse(portals.get(0));
    }

    @Override
    public List<SelfServicePortalResponse> getSelfServicePortalsByEmployee(String employeeId) {
        Employee employee = employeeRepository.findById(Long.valueOf(employeeId)).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return selfServicePortalRepository.findByEmployee(employee).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<SelfServicePortalResponse> getAllSelfServicePortals() {
        return new ArrayList<>(portalList);
    }

    private SelfServicePortalResponse mapToResponse(SelfServicePortal portal) {
        SelfServicePortalResponse response = new SelfServicePortalResponse();
        response.setId(portal.getId());
        response.setEmployeeId(portal.getEmployee().getId());
        response.setFeature(portal.getFeature());
        response.setDescription(portal.getDescription());
        return response;
    }
}
