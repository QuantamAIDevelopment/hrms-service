package com.qaid.hrms.service.impl;

import com.qaid.hrms.model.dto.request.TimeTrackingRequest;
import com.qaid.hrms.model.dto.response.TimeTrackingResponse;
import com.qaid.hrms.service.TimeTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.repository.EmployeeRepository;
import com.qaid.hrms.model.entity.TimeTracking;
import com.qaid.hrms.repository.TimeTrackingRepository;
import com.qaid.hrms.exception.ResourceNotFoundException;

@Service
public class TimeTrackingServiceImpl implements TimeTrackingService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TimeTrackingRepository timeTrackingRepository;

    @Override
    public TimeTrackingResponse createTimeTracking(TimeTrackingRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        TimeTracking timeTracking = new TimeTracking();
        timeTracking.setEmployee(employee);
        timeTracking.setCheckInTime(request.getCheckInTime());
        timeTracking.setCheckOutTime(request.getCheckOutTime());
        timeTracking.setTaskDescription(request.getTaskDescription());
        return mapToResponse(timeTrackingRepository.save(timeTracking));
    }
    @Override
    public TimeTrackingResponse updateTimeTrackingByEmployeeId(String employeeId, TimeTrackingRequest request) {
        Employee employee = employeeRepository.findById(Long.valueOf(employeeId)).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<TimeTracking> records = timeTrackingRepository.findByEmployee(employee);
        if (records.isEmpty()) throw new ResourceNotFoundException("No time tracking found for employee");
        TimeTracking timeTracking = records.get(0);
        timeTracking.setCheckInTime(request.getCheckInTime());
        timeTracking.setCheckOutTime(request.getCheckOutTime());
        timeTracking.setTaskDescription(request.getTaskDescription());
        return mapToResponse(timeTrackingRepository.save(timeTracking));
    }
    @Override
    public void deleteTimeTrackingByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findById(Long.valueOf(employeeId)).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<TimeTracking> records = timeTrackingRepository.findByEmployee(employee);
        if (records.isEmpty()) throw new ResourceNotFoundException("No time tracking found for employee");
        timeTrackingRepository.delete(records.get(0));
    }
    @Override
    public TimeTrackingResponse getTimeTrackingByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findById(Long.valueOf(employeeId)).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<TimeTracking> records = timeTrackingRepository.findByEmployee(employee);
        if (records.isEmpty()) throw new ResourceNotFoundException("No time tracking found for employee");
        return mapToResponse(records.get(0));
    }
    @Override
    public List<TimeTrackingResponse> getTimeTrackingByEmployee(String employeeId) {
        Employee employee = employeeRepository.findById(Long.valueOf(employeeId)).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return timeTrackingRepository.findByEmployee(employee).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }
    @Override
    public List<TimeTrackingResponse> getAllTimeTrackingRecords() {
        return List.of(getTimeTrackingByEmployeeId("1"));
    }

    private TimeTrackingResponse mapToResponse(TimeTracking timeTracking) {
        TimeTrackingResponse response = new TimeTrackingResponse();
        response.setId(timeTracking.getId());
        response.setEmployeeId(timeTracking.getEmployee().getId());
        response.setCheckInTime(timeTracking.getCheckInTime());
        response.setCheckOutTime(timeTracking.getCheckOutTime());
        response.setTaskDescription(timeTracking.getTaskDescription());
        return response;
    }
}
