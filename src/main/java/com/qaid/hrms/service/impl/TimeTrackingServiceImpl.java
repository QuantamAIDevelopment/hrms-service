package com.qaid.hrms.service.impl;

import com.qaid.hrms.model.dto.request.TimeTrackingRequest;
import com.qaid.hrms.model.dto.response.TimeTrackingResponse;
import com.qaid.hrms.service.TimeTrackingService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TimeTrackingServiceImpl implements TimeTrackingService {
    @Override
    public TimeTrackingResponse createTimeTracking(TimeTrackingRequest request) {
        TimeTrackingResponse response = new TimeTrackingResponse();
        response.setId(1L);
        response.setEmployeeId(request.getEmployeeId());
        response.setCheckInTime(request.getCheckInTime());
        response.setCheckOutTime(request.getCheckOutTime());
        response.setTaskDescription(request.getTaskDescription());
        return response;
    }
    @Override
    public TimeTrackingResponse updateTimeTrackingByEmployeeId(String employeeId, TimeTrackingRequest request) {
        TimeTrackingResponse response = new TimeTrackingResponse();
        response.setId(1L);
        response.setEmployeeId(Long.parseLong(employeeId));
        response.setCheckInTime(request.getCheckInTime());
        response.setCheckOutTime(request.getCheckOutTime());
        response.setTaskDescription(request.getTaskDescription());
        return response;
    }
    @Override
    public void deleteTimeTrackingByEmployeeId(String employeeId) {
        // Example business logic: delete operation
    }
    @Override
    public TimeTrackingResponse getTimeTrackingByEmployeeId(String employeeId) {
        TimeTrackingResponse response = new TimeTrackingResponse();
        response.setId(1L);
        response.setEmployeeId(Long.parseLong(employeeId));
        response.setCheckInTime(null);
        response.setCheckOutTime(null);
        response.setTaskDescription("Sample");
        return response;
    }
    @Override
    public List<TimeTrackingResponse> getTimeTrackingByEmployee(String employeeId) {
        return List.of(getTimeTrackingByEmployeeId(employeeId));
    }
    @Override
    public List<TimeTrackingResponse> getAllTimeTrackingRecords() {
        return List.of(getTimeTrackingByEmployeeId("1"));
    }
}
