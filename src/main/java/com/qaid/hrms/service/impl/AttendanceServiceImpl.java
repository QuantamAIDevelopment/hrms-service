package com.qaid.hrms.service.impl;

import com.qaid.hrms.exception.BadRequestException;
import com.qaid.hrms.exception.ResourceNotFoundException;
import com.qaid.hrms.model.dto.request.AttendanceRequest;
import com.qaid.hrms.model.dto.response.AttendanceResponse;
import com.qaid.hrms.model.entity.Attendance;
import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.repository.AttendanceRepository;
import com.qaid.hrms.repository.EmployeeRepository;
import com.qaid.hrms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public AttendanceResponse createAttendance(AttendanceRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setInTime(request.getInTime());
        attendance.setOutTime(request.getOutTime());
        attendance.setNote(request.getNote());
        attendance.setStatus(request.getStatus() != null ? request.getStatus() : "Pending");

        return mapToResponse(attendanceRepository.save(attendance));
    }


    @Override
    public List<AttendanceResponse> getAttendanceByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        System.out.println("Attendence from DB-->"+attendanceRepository.findByInTimeBetween(startOfDay, endOfDay));
        return attendanceRepository.findByInTimeBetween(startOfDay, endOfDay).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }



    @Override
    public AttendanceResponse updateAttendanceByEmployeeId(String employeeId, AttendanceRequest attendanceRequest) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Attendance> attendances = attendanceRepository.findByEmployeeId(employee.getId());
        if (attendances.isEmpty()) {
            throw new ResourceNotFoundException("Attendance record not found for employee");
        }
        Attendance attendance = attendances.get(0); // Assuming update the latest
        if (attendanceRequest.getInTime() != null) attendance.setInTime(attendanceRequest.getInTime());
        if (attendanceRequest.getOutTime() != null) attendance.setOutTime(attendanceRequest.getOutTime());
        if (attendanceRequest.getNote() != null) attendance.setNote(attendanceRequest.getNote());
        if (attendanceRequest.getStatus() != null) attendance.setStatus(attendanceRequest.getStatus());
        return mapToResponse(attendanceRepository.save(attendance));
    }

    @Override
    public void deleteAttendanceByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Attendance> attendances = attendanceRepository.findByEmployeeId(employee.getId());
        if (attendances.isEmpty()) {
            throw new ResourceNotFoundException("Attendance record not found for employee");
        }
        attendanceRepository.deleteAll(attendances);
    }

    @Override
    public AttendanceResponse getAttendanceByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Attendance> attendances = attendanceRepository.findByEmployeeId(employee.getId());
        if (attendances.isEmpty()) {
            throw new ResourceNotFoundException("Attendance record not found for employee");
        }
        return mapToResponse(attendances.get(0));
    }

    @Override
    public List<AttendanceResponse> getAllAttendance() {
        return attendanceRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponse> getAttendanceByEmployee(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return attendanceRepository.findByEmployeeId(employee.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponse> getAttendanceByEmployeeAndDate(String employeeId, LocalDate date) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return attendanceRepository.findByEmployeeIdAndInTimeBetween(employee.getId(), startOfDay, endOfDay).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AttendanceResponse checkIn(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        List<Attendance> existingAttendance = attendanceRepository
                .findByEmployeeIdAndInTimeBetween(employee.getId(), today.atStartOfDay(), today.plusDays(1).atStartOfDay());
        if (!existingAttendance.isEmpty()) {
            throw new BadRequestException("Employee already checked in today");
        }
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setInTime(now);
        attendance.setStatus("Present");
        return mapToResponse(attendanceRepository.save(attendance));
    }

    @Override
    public AttendanceResponse checkOut(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndInTimeBetween(employee.getId(), today.atStartOfDay(), today.plusDays(1).atStartOfDay())
                .stream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("No check-in record found for today"));
        if (attendance.getOutTime() != null) {
            throw new BadRequestException("Employee already checked out today");
        }
        attendance.setOutTime(now);
        attendance.setStatus("Completed");
        return mapToResponse(attendanceRepository.save(attendance));
    }

    private AttendanceResponse mapToResponse(Attendance attendance) {
        AttendanceResponse response = new AttendanceResponse();
        response.setId(attendance.getId());
        response.setEmployeeId(attendance.getEmployee().getEmployeeId());
        response.setEmployeeName(attendance.getEmployee().getFirstName() + " " + attendance.getEmployee().getLastName());
        response.setInTime(attendance.getInTime());
        response.setOutTime(attendance.getOutTime());
        response.setNote(attendance.getNote());
        response.setStatus(attendance.getStatus());
        response.setCreatedAt(attendance.getCreatedAt());
        response.setUpdatedAt(attendance.getUpdatedAt());
        return response;
    }
}