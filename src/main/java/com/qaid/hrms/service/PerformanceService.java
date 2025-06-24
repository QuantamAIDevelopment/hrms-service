package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.PerformanceRequest;
import com.qaid.hrms.model.dto.response.PerformanceResponse;
import java.util.List;

public interface PerformanceService {
    PerformanceResponse createPerformance(PerformanceRequest request);
    PerformanceResponse updatePerformanceByEmployeeId(String employeeId, PerformanceRequest request);
    void deletePerformanceByEmployeeId(String employeeId);
    PerformanceResponse getPerformanceByEmployeeId(String employeeId);
    List<PerformanceResponse> getAllPerformances();
    List<PerformanceResponse> getPerformancesByEmployee(String employeeId);
}
