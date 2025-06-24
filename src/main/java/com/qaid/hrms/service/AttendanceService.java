package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.AttendanceRequest;
import com.qaid.hrms.model.dto.response.AttendanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    AttendanceResponse createAttendance(AttendanceRequest attendanceRequest);
    AttendanceResponse updateAttendanceByEmployeeId(String employeeId, AttendanceRequest attendanceRequest);
    void deleteAttendanceByEmployeeId(String employeeId);
    AttendanceResponse getAttendanceByEmployeeId(String employeeId);
    List<AttendanceResponse> getAllAttendance();
    List<AttendanceResponse> getAttendanceByEmployee(String employeeId);
    List<AttendanceResponse> getAttendanceByDate(LocalDate date);
    List<AttendanceResponse> getAttendanceByEmployeeAndDate(String employeeId, LocalDate date);
    AttendanceResponse checkIn(String employeeId);
    AttendanceResponse checkOut(String employeeId);
}