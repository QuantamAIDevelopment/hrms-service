package com.qaid.hrms.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaid.hrms.exception.BadRequestException;
import com.qaid.hrms.exception.ResourceNotFoundException;
import com.qaid.hrms.model.dto.request.ReportRequest;
import com.qaid.hrms.model.dto.response.ReportResponse;
import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.model.entity.Report;
import com.qaid.hrms.repository.EmployeeRepository;
import com.qaid.hrms.repository.ReportRepository;
import com.qaid.hrms.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${app.report.dir:reports}")
    private String reportDir;

    @Value("${app.report.templates.dir:report-templates}")
    private String templatesDir;

    @Override
    @Transactional
    public ReportResponse generateReport(ReportRequest request) throws IOException {
        // Get current user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Validate date range if provided
        if (request.getStartDate() != null && request.getEndDate() != null) {
            if (request.getStartDate().isAfter(request.getEndDate())) {
                throw new BadRequestException("Start date cannot be after end date");
            }
        }

        // Generate report file
        String fileName = UUID.randomUUID().toString() + "_" + request.getReportName() + ".pdf";
        Path reportPath = Paths.get(reportDir).resolve(fileName);

        try {
            Files.createDirectories(reportPath.getParent());
            
            // Load report template
            String templatePath = templatesDir + "/" + request.getReportType().toLowerCase() + ".jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    new ClassPathResource(templatePath).getInputStream());

            // Prepare report parameters
            Map<String, Object> parameters = new HashMap<>();
            if (request.getParameters() != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> paramMap = objectMapper.readValue(request.getParameters(), Map.class);
                parameters.putAll(paramMap);
            }
            parameters.put("startDate", request.getStartDate());
            parameters.put("endDate", request.getEndDate());
            parameters.put("generatedBy", employee.getFirstName() + " " + employee.getLastName());
            parameters.put("generatedAt", LocalDate.now());

            // Get data source based on report type
            JRDataSource dataSource = getDataSourceForReportType(request.getReportType(), request);

            // Generate report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath.toString());

        } catch (IOException | JRException e) {
            throw new BadRequestException("Failed to generate report: " + e.getMessage());
        }

        Report report = new Report();
        report.setReportType(request.getReportType());
        report.setReportName(request.getReportName());
        report.setStartDate(request.getStartDate());
        report.setEndDate(request.getEndDate());
        report.setFilePath(reportPath.toString());
        report.setFileName(fileName);
        report.setFileSize(Files.size(reportPath));
        report.setMimeType("application/pdf");
        report.setParameters(request.getParameters());
        report.setDescription(request.getDescription());
        report.setGeneratedBy(employee);

        return mapToResponse(reportRepository.save(report));
    }

    @Override
    public ReportResponse getReportByAllocatedId(String allocatedId) {
        // allocatedId is assumed to be the fileName (UUID_reportName.pdf)
        Report report = reportRepository.findAll().stream()
            .filter(r -> r.getFileName().equals(allocatedId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Report not found with allocatedId: " + allocatedId));
        return mapToResponse(report);
    }

    private JRDataSource getDataSourceForReportType(String reportType, ReportRequest request) {
        switch (reportType.toLowerCase()) {
            case "attendance":
                return generateAttendanceReport(request);
            case "salary":
                return generateSalaryReport(request);
            case "leave":
                return generateLeaveReport(request);
            case "employee":
                return generateEmployeeReport(request);
            default:
                throw new BadRequestException("Unsupported report type: " + reportType);
        }
    }

    private JRDataSource generateAttendanceReport(ReportRequest request) {
        // TODO: Implement attendance report data source
        // This would typically fetch attendance records from the database
        // and format them for the report
        return new JRBeanCollectionDataSource(new ArrayList<>());
    }

    private JRDataSource generateSalaryReport(ReportRequest request) {
        // TODO: Implement salary report data source
        // This would typically fetch salary records from the database
        // and format them for the report
        return new JRBeanCollectionDataSource(new ArrayList<>());
    }

    private JRDataSource generateLeaveReport(ReportRequest request) {
        // TODO: Implement leave report data source
        // This would typically fetch leave records from the database
        // and format them for the report
        return new JRBeanCollectionDataSource(new ArrayList<>());
    }

    private JRDataSource generateEmployeeReport(ReportRequest request) {
        // TODO: Implement employee report data source
        // This would typically fetch employee records from the database
        // and format them for the report
        return new JRBeanCollectionDataSource(new ArrayList<>());
    }

    @Override
    public List<ReportResponse> getAllReports() {
        return reportRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportResponse> getReportsByType(String reportType) {
        return reportRepository.findByReportType(reportType).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportResponse> getReportsByDateRange(LocalDate startDate, LocalDate endDate) {
        return reportRepository.findByStartDateBetweenOrEndDateBetween(startDate, endDate, startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportResponse> getReportsByTypeAndDateRange(String reportType, LocalDate startDate, LocalDate endDate) {
        return reportRepository.findByReportTypeAndStartDateBetweenOrEndDateBetween(
                reportType, startDate, endDate, startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public byte[] downloadReportByAllocatedId(String allocatedId) {
        Report report = reportRepository.findAll().stream()
            .filter(r -> r.getFileName().equals(allocatedId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Report not found with allocatedId: " + allocatedId));
        try {
            Path filePath = Paths.get(report.getFilePath());
            return Files.readAllBytes(filePath);
        } catch (IOException ex) {
            throw new BadRequestException("Could not download file. Please try again!");
        }
    }

    @Override
    public void deleteReportByAllocatedId(String allocatedId) {
        Report report = reportRepository.findAll().stream()
            .filter(r -> r.getFileName().equals(allocatedId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Report not found with allocatedId: " + allocatedId));
        try {
            Files.deleteIfExists(Paths.get(report.getFilePath()));
        } catch (IOException ex) {
            throw new BadRequestException("Could not delete file. Please try again!");
        }
        reportRepository.delete(report);
    }


    private ReportResponse mapToResponse(Report report) {
        ReportResponse response = new ReportResponse();
        response.setId(report.getId());
        response.setReportType(report.getReportType());
        response.setReportName(report.getReportName());
        response.setStartDate(report.getStartDate());
        response.setEndDate(report.getEndDate());
        response.setFilePath(report.getFilePath());
        response.setFileName(report.getFileName());
        response.setFileSize(report.getFileSize());
        response.setMimeType(report.getMimeType());
        response.setParameters(report.getParameters());
        response.setDescription(report.getDescription());
        response.setGeneratedBy(report.getGeneratedBy().getFirstName() + " " + report.getGeneratedBy().getLastName());
        response.setCreatedAt(report.getCreatedAt());
        response.setUpdatedAt(report.getUpdatedAt());
        return response;
    }
}