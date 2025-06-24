package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {
    List<TimeSheet> findByEmployeeId(Long employeeId);
    List<TimeSheet> findByDate(LocalDate date);
    List<TimeSheet> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
    boolean existsByEmployeeIdAndDate(Long employeeId, LocalDate date);
}