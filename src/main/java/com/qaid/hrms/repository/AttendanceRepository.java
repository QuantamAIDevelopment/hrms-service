package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeId(Long employeeId);
    List<Attendance> findByInTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Attendance> findByEmployeeIdAndInTimeBetween(Long employeeId, LocalDateTime start, LocalDateTime end);
}