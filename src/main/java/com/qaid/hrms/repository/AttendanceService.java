package com.qaid.hrms.repository;

import com.qaid.hrms.model.dto.request.AttendanceRequest;
import com.qaid.hrms.model.dto.response.AttendanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    AttendanceResponse createAttendance(AttendanceRequest attendanceRequest);
    AttendanceResponse updateAttendance(Long id, AttendanceRequest attendanceRequest);
    void deleteAttendance(Long id);
    AttendanceResponse getAttendanceById(Long id);
    List<AttendanceResponse> getAllAttendance();
    List<AttendanceResponse> getAttendanceByEmployee(Long employeeId);
    List<AttendanceResponse> getAttendanceByDate(LocalDate date);
    List<AttendanceResponse> getAttendanceByEmployeeAndDate(Long employeeId, LocalDate date);
    AttendanceResponse checkIn(Long employeeId);
    AttendanceResponse checkOut(Long employeeId);
} 