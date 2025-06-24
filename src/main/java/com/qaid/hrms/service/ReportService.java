package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.ReportRequest;
import com.qaid.hrms.model.dto.response.ReportResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    ReportResponse generateReport(ReportRequest reportRequest) throws IOException;
    ReportResponse getReportByAllocatedId(String allocatedId);
    List<ReportResponse> getAllReports();
    List<ReportResponse> getReportsByType(String reportType);
    List<ReportResponse> getReportsByDateRange(LocalDate startDate, LocalDate endDate);
    List<ReportResponse> getReportsByTypeAndDateRange(String reportType, LocalDate startDate, LocalDate endDate);
    byte[] downloadReportByAllocatedId(String allocatedId);
    void deleteReportByAllocatedId(String allocatedId);
}