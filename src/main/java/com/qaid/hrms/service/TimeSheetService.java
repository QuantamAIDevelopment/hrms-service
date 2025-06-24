package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.TimeSheetRequest;
import com.qaid.hrms.model.dto.response.TimeSheetResponse;

import java.time.LocalDate;
import java.util.List;

public interface TimeSheetService {
    TimeSheetResponse createTimeSheet(TimeSheetRequest timeSheetRequest);
    TimeSheetResponse updateTimeSheetByEmployeeId(String employeeId, TimeSheetRequest timeSheetRequest);
    void deleteTimeSheetByEmployeeId(String employeeId);
    TimeSheetResponse getTimeSheetByEmployeeId(String employeeId);
    List<TimeSheetResponse> getAllTimeSheets();
    List<TimeSheetResponse> getTimeSheetsByEmployee(String employeeId);
    List<TimeSheetResponse> getTimeSheetsByDate(LocalDate date);
    List<TimeSheetResponse> getTimeSheetsByEmployeeAndDate(String employeeId, LocalDate date);
    TimeSheetResponse approveTimeSheetByEmployeeId(String employeeId, String comments);
    TimeSheetResponse rejectTimeSheetByEmployeeId(String employeeId, String comments);
}