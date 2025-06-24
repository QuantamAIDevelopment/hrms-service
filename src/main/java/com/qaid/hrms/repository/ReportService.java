package com.qaid.hrms.repository;

import com.qaid.hrms.model.dto.request.ReportRequest;
import com.qaid.hrms.model.dto.response.ReportResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    ReportResponse generateReport(ReportRequest reportRequest) throws IOException;
    ReportResponse getReportById(Long id);
    List<ReportResponse> getAllReports();
    List<ReportResponse> getReportsByType(String reportType);
    List<ReportResponse> getReportsByDateRange(LocalDate startDate, LocalDate endDate);
    List<ReportResponse> getReportsByTypeAndDateRange(String reportType, LocalDate startDate, LocalDate endDate);
    byte[] downloadReport(Long id);
    void deleteReport(Long id);
} 