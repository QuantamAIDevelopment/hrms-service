package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.TimeTrackingRequest;
import com.qaid.hrms.model.dto.response.TimeTrackingResponse;

import java.util.List;

public interface TimeTrackingService {

    TimeTrackingResponse createTimeTracking(TimeTrackingRequest request);

    TimeTrackingResponse updateTimeTracking(Long id, TimeTrackingRequest request);

    void deleteTimeTracking(Long id);

    TimeTrackingResponse getTimeTrackingById(Long id);

    List<TimeTrackingResponse> getTimeTrackingByEmployee(Long employeeId);

    List<TimeTrackingResponse> getAllTimeTrackingRecords();
}
