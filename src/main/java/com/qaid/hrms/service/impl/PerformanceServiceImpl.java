package com.qaid.hrms.service.impl;

import com.qaid.hrms.model.dto.request.PerformanceRequest;
import com.qaid.hrms.model.dto.response.PerformanceResponse;
import com.qaid.hrms.service.PerformanceService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {
    @Override
    public PerformanceResponse createPerformance(PerformanceRequest request) {
        PerformanceResponse response = new PerformanceResponse();
        response.setId(1L);
        response.setEmployeeId(request.getEmployeeId());
        response.setScore(request.getScore());
        return response;
    }
    @Override
    public PerformanceResponse updatePerformanceByEmployeeId(String employeeId, PerformanceRequest request) {
        PerformanceResponse response = new PerformanceResponse();
        response.setId(1L);
        response.setEmployeeId(Long.parseLong(employeeId));
        response.setScore(request.getScore());
        return response;
    }
    @Override
    public void deletePerformanceByEmployeeId(String employeeId) {
        // Example business logic: delete operation
    }
    @Override
    public PerformanceResponse getPerformanceByEmployeeId(String employeeId) {
        PerformanceResponse response = new PerformanceResponse();
        response.setId(1L);
        response.setEmployeeId(Long.parseLong(employeeId));
        response.setScore(90);
        return response;
    }
    @Override
    public List<PerformanceResponse> getAllPerformances() {
        return List.of(getPerformanceByEmployeeId("1"));
    }
    @Override
    public List<PerformanceResponse> getPerformancesByEmployee(String employeeId) {
        return List.of(getPerformanceByEmployeeId(employeeId));
    }
}
