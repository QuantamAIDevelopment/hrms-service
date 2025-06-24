package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.PerformanceRequest;
import com.qaid.hrms.model.dto.response.PerformanceResponse;
import java.util.List;

public interface PerformanceService {
    PerformanceResponse createPerformance(PerformanceRequest request);
    PerformanceResponse updatePerformance(Long id, PerformanceRequest request);
    void deletePerformance(Long id);
    PerformanceResponse getPerformanceById(Long id);
    List<PerformanceResponse> getAllPerformances();
    List<PerformanceResponse> getPerformancesByEmployee(Long employeeId);
}
