package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.PerformanceReviewRequest;
import com.qaid.hrms.model.dto.response.PerformanceReviewResponse;

import java.util.List;

public interface PerformanceManagementService {
    PerformanceReviewResponse createPerformanceReview(PerformanceReviewRequest request);

    PerformanceReviewResponse updatePerformanceReviewByEmployeeId(String employeeId, PerformanceReviewRequest request);

    void deletePerformanceReviewByEmployeeId(String employeeId);

    PerformanceReviewResponse getPerformanceReviewByEmployeeId(String employeeId);

    List<PerformanceReviewResponse> getPerformanceReviewsByEmployee(String employeeId);

    List<PerformanceReviewResponse> getAllPerformanceReviews();
}
