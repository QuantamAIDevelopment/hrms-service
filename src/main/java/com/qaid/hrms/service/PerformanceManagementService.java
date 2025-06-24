package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.PerformanceReviewRequest;
import com.qaid.hrms.model.dto.response.PerformanceReviewResponse;

import java.util.List;

public interface PerformanceManagementService {

    PerformanceReviewResponse createPerformanceReview(PerformanceReviewRequest request);

    PerformanceReviewResponse updatePerformanceReview(Long id, PerformanceReviewRequest request);

    void deletePerformanceReview(Long id);

    PerformanceReviewResponse getPerformanceReviewById(Long id);

    List<PerformanceReviewResponse> getPerformanceReviewsByEmployee(Long employeeId);

    List<PerformanceReviewResponse> getAllPerformanceReviews();
}
