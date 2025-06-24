package com.qaid.hrms.service.impl;

import com.qaid.hrms.exception.ResourceNotFoundException;
import com.qaid.hrms.model.dto.request.PerformanceReviewRequest;
import com.qaid.hrms.model.dto.response.PerformanceReviewResponse;
import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.model.entity.PerformanceReview;
import com.qaid.hrms.repository.EmployeeRepository;
import com.qaid.hrms.repository.PerformanceReviewRepository;
import com.qaid.hrms.service.PerformanceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceManagementServiceImpl implements PerformanceManagementService {

    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public PerformanceReviewResponse createPerformanceReview(PerformanceReviewRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        PerformanceReview review = new PerformanceReview();
        review.setEmployeeId(employee.getId());
        review.setReviewDate(request.getReviewDate());
        review.setReviewer(request.getReviewer());
        review.setComments(request.getComments());
        review.setRating(request.getRating());
        return mapToResponse(performanceReviewRepository.save(review));
    }

    @Override
    @Transactional
    public PerformanceReviewResponse updatePerformanceReviewByEmployeeId(String employeeId, PerformanceReviewRequest request) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<PerformanceReview> reviews = performanceReviewRepository.findByEmployeeId(employee.getId());
        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No performance review found for employee");
        }
        PerformanceReview review = reviews.get(0); // For demo, update the first review
        if (request.getReviewDate() != null) review.setReviewDate(request.getReviewDate());
        if (request.getReviewer() != null) review.setReviewer(request.getReviewer());
        if (request.getComments() != null) review.setComments(request.getComments());
        if (request.getRating() != null) review.setRating(request.getRating());
        return mapToResponse(performanceReviewRepository.save(review));
    }

    @Override
    @Transactional
    public void deletePerformanceReviewByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<PerformanceReview> reviews = performanceReviewRepository.findByEmployeeId(employee.getId());
        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No performance review found for employee");
        }
        performanceReviewRepository.delete(reviews.get(0)); // For demo, delete the first review
    }

    @Override
    public PerformanceReviewResponse getPerformanceReviewByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<PerformanceReview> reviews = performanceReviewRepository.findByEmployeeId(employee.getId());
        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No performance review found for employee");
        }
        return mapToResponse(reviews.get(0)); // For demo, return the first review
    }

    @Override
    public List<PerformanceReviewResponse> getPerformanceReviewsByEmployee(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return performanceReviewRepository.findByEmployeeId(employee.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PerformanceReviewResponse> getAllPerformanceReviews() {
        return performanceReviewRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PerformanceReviewResponse mapToResponse(PerformanceReview review) {
        PerformanceReviewResponse response = new PerformanceReviewResponse();
        response.setId(review.getId());
        response.setEmployeeId(review.getEmployeeId());
        response.setReviewer(review.getReviewer());
        response.setReviewDate(review.getReviewDate());
        response.setComments(review.getComments());
        response.setRating(review.getRating());
        return response;
    }
}
