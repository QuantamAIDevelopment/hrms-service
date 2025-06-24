package com.qaid.hrms.service.impl;

import com.qaid.hrms.exception.BadRequestException;
import com.qaid.hrms.exception.ResourceNotFoundException;
import com.qaid.hrms.model.dto.request.TimeSheetRequest;
import com.qaid.hrms.model.dto.response.TimeSheetResponse;
import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.model.entity.TimeSheet;
import com.qaid.hrms.repository.EmployeeRepository;
import com.qaid.hrms.repository.TimeSheetRepository;
import com.qaid.hrms.service.TimeSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeSheetServiceImpl implements TimeSheetService {

    @Autowired
    private TimeSheetRepository timeSheetRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public TimeSheetResponse createTimeSheet(TimeSheetRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Check if timesheet already exists for the date
        if (timeSheetRepository.existsByEmployeeIdAndDate(request.getEmployeeId(), request.getDate())) {
            throw new BadRequestException("TimeSheet already exists for this date");
        }

        // Validate time range
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new BadRequestException("Start time cannot be after end time");
        }

        TimeSheet timeSheet = new TimeSheet();
        timeSheet.setEmployee(employee);
        timeSheet.setDate(request.getDate());
        timeSheet.setStartTime(request.getStartTime());
        timeSheet.setEndTime(request.getEndTime());
        timeSheet.setBreakDuration(request.getBreakDuration() != null ? request.getBreakDuration() : 0);
        timeSheet.setDescription(request.getDescription());
        timeSheet.setStatus("Pending");

        // Calculate total hours
        Duration duration = Duration.between(request.getStartTime(), request.getEndTime());
        double totalMinutes = duration.toMinutes() - timeSheet.getBreakDuration();
        timeSheet.setTotalHours(totalMinutes / 60.0);

        return mapToResponse(timeSheetRepository.save(timeSheet));
    }

    @Override
    public List<TimeSheetResponse> getTimeSheetsByDate(LocalDate date) {
        return timeSheetRepository.findByDate(date).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TimeSheetResponse> getAllTimeSheets() {
        return timeSheetRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TimeSheetResponse updateTimeSheetByEmployeeId(String employeeId, TimeSheetRequest request) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<TimeSheet> timeSheets = timeSheetRepository.findByEmployeeId(employee.getId());
        if (timeSheets.isEmpty()) {
            throw new ResourceNotFoundException("No timesheets found for employee");
        }
        TimeSheet timeSheet = timeSheets.get(0); // For demo, update the first timesheet
        if (request.getDate() != null) {
            timeSheet.setDate(request.getDate());
        }
        if (request.getStartTime() != null) {
            timeSheet.setStartTime(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            timeSheet.setEndTime(request.getEndTime());
        }
        if (request.getBreakDuration() != null) {
            timeSheet.setBreakDuration(request.getBreakDuration());
        }
        if (request.getDescription() != null) {
            timeSheet.setDescription(request.getDescription());
        }
        // Recalculate total hours
        java.time.Duration duration = java.time.Duration.between(timeSheet.getStartTime(), timeSheet.getEndTime());
        double totalMinutes = duration.toMinutes() - timeSheet.getBreakDuration();
        timeSheet.setTotalHours(totalMinutes / 60.0);
        return mapToResponse(timeSheetRepository.save(timeSheet));
    }

    @Override
    public void deleteTimeSheetByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<TimeSheet> timeSheets = timeSheetRepository.findByEmployeeId(employee.getId());
        for (TimeSheet timeSheet : timeSheets) {
            if (!timeSheet.getStatus().equals("Pending")) {
                throw new BadRequestException("Cannot delete approved or rejected timesheet");
            }
            timeSheetRepository.deleteById(timeSheet.getId());
        }
    }

    @Override
    public TimeSheetResponse getTimeSheetByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<TimeSheet> timeSheets = timeSheetRepository.findByEmployeeId(employee.getId());
        if (timeSheets.isEmpty()) {
            throw new ResourceNotFoundException("No timesheets found for employee");
        }
        return mapToResponse(timeSheets.get(0)); // For demo, return the first timesheet
    }

    @Override
    public List<TimeSheetResponse> getTimeSheetsByEmployee(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return timeSheetRepository.findByEmployeeId(employee.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TimeSheetResponse> getTimeSheetsByEmployeeAndDate(String employeeId, LocalDate date) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return timeSheetRepository.findByEmployeeIdAndDate(employee.getId(), date).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TimeSheetResponse approveTimeSheetByEmployeeId(String employeeId, String comments) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<TimeSheet> timeSheets = timeSheetRepository.findByEmployeeId(employee.getId());
        if (timeSheets.isEmpty()) {
            throw new ResourceNotFoundException("No timesheets found for employee");
        }
        TimeSheet timeSheet = timeSheets.get(0); // For demo, approve the first timesheet
        if (!timeSheet.getStatus().equals("Pending")) {
            throw new BadRequestException("TimeSheet is not in pending status");
        }
        timeSheet.setStatus("Approved");
        timeSheet.setComments(comments);
        return mapToResponse(timeSheetRepository.save(timeSheet));
    }

    @Override
    public TimeSheetResponse rejectTimeSheetByEmployeeId(String employeeId, String comments) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<TimeSheet> timeSheets = timeSheetRepository.findByEmployeeId(employee.getId());
        if (timeSheets.isEmpty()) {
            throw new ResourceNotFoundException("No timesheets found for employee");
        }
        TimeSheet timeSheet = timeSheets.get(0); // For demo, reject the first timesheet
        if (!timeSheet.getStatus().equals("Pending")) {
            throw new BadRequestException("TimeSheet is not in pending status");
        }
        timeSheet.setStatus("Rejected");
        timeSheet.setComments(comments);
        return mapToResponse(timeSheetRepository.save(timeSheet));
    }

    private TimeSheetResponse mapToResponse(TimeSheet timeSheet) {
        TimeSheetResponse response = new TimeSheetResponse();
        response.setId(timeSheet.getId());
        response.setEmployeeId(timeSheet.getEmployee().getEmployeeId());
        response.setEmployeeName(timeSheet.getEmployee().getFirstName() + " " + timeSheet.getEmployee().getLastName());
        response.setDate(timeSheet.getDate());
        response.setStartTime(timeSheet.getStartTime());
        response.setEndTime(timeSheet.getEndTime());
        response.setBreakDuration(timeSheet.getBreakDuration());
        response.setTotalHours(timeSheet.getTotalHours());
        response.setDescription(timeSheet.getDescription());
        response.setStatus(timeSheet.getStatus());
        response.setComments(timeSheet.getComments());
        response.setCreatedAt(timeSheet.getCreatedAt());
        response.setUpdatedAt(timeSheet.getUpdatedAt());
        return response;
    }
}