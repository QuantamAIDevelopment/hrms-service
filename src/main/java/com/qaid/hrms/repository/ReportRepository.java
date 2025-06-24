package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByReportType(String reportType);
    
    List<Report> findByStartDateBetweenOrEndDateBetween(
            LocalDate startDate1, LocalDate endDate1,
            LocalDate startDate2, LocalDate endDate2);
    
    List<Report> findByReportTypeAndStartDateBetweenOrEndDateBetween(
            String reportType,
            LocalDate startDate1, LocalDate endDate1,
            LocalDate startDate2, LocalDate endDate2);
}