package com.qaid.hrms.repository;

import com.qaid.hrms.model.dto.request.TimeSheetRequest;
import com.qaid.hrms.model.dto.response.TimeSheetResponse;

import java.time.LocalDate;
import java.util.List;

public interface TimeSheetService {
    TimeSheetResponse createTimeSheet(TimeSheetRequest timeSheetRequest);
    TimeSheetResponse updateTimeSheet(Long id, TimeSheetRequest timeSheetRequest);
    void deleteTimeSheet(Long id);
    TimeSheetResponse getTimeSheetById(Long id);
    List<TimeSheetResponse> getAllTimeSheets();
    List<TimeSheetResponse> getTimeSheetsByEmployee(Long employeeId);
    List<TimeSheetResponse> getTimeSheetsByDate(LocalDate date);
    List<TimeSheetResponse> getTimeSheetsByEmployeeAndDate(Long employeeId, LocalDate date);
    TimeSheetResponse approveTimeSheet(Long id, String comments);
    TimeSheetResponse rejectTimeSheet(Long id, String comments);
} 