package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.TimeTrackingRequest;
import com.qaid.hrms.model.dto.response.TimeTrackingResponse;

import java.util.List;

public interface TimeTrackingService {
    TimeTrackingResponse createTimeTracking(TimeTrackingRequest request);
    TimeTrackingResponse updateTimeTrackingByEmployeeId(String employeeId, TimeTrackingRequest request);
    void deleteTimeTrackingByEmployeeId(String employeeId);
    TimeTrackingResponse getTimeTrackingByEmployeeId(String employeeId);
    List<TimeTrackingResponse> getTimeTrackingByEmployee(String employeeId);
    List<TimeTrackingResponse> getAllTimeTrackingRecords();
}
