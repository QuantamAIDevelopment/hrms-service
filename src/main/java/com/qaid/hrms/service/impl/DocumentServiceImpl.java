package com.qaid.hrms.service.impl;

import com.qaid.hrms.exception.BadRequestException;
import com.qaid.hrms.exception.ResourceNotFoundException;
import com.qaid.hrms.model.dto.request.DocumentRequest;
import com.qaid.hrms.model.dto.response.DocumentResponse;
import com.qaid.hrms.model.entity.Document;
import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.repository.DocumentRepository;
import com.qaid.hrms.repository.EmployeeRepository;
import com.qaid.hrms.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Override
    @Transactional
    public DocumentResponse uploadDocument(DocumentRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        MultipartFile file = request.getFile();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            Path targetLocation = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Document document = new Document();
            document.setEmployee(employee);
            document.setDocumentType(request.getDocumentType());
            document.setFileName(fileName);
            document.setFilePath(targetLocation.toString());
            document.setFileSize(file.getSize());
            document.setMimeType(file.getContentType());
            document.setDescription(request.getDescription());
            document.setExpiryDate(request.getExpiryDate());
            document.setStatus("Active");

            return mapToResponse(documentRepository.save(document));
        } catch (IOException ex) {
            throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
        }
    }


    @Override
    public List<DocumentResponse> getAllDocuments() {
        return documentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentResponse> getDocumentsByType(String documentType) {
        return documentRepository.findByDocumentType(documentType).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DocumentResponse updateDocumentByEmployeeId(String employeeId, DocumentRequest documentRequest) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Document> documents = documentRepository.findByEmployeeId(employee.getId());
        if (documents.isEmpty()) {
            throw new ResourceNotFoundException("No documents found for employee");
        }
        Document document = documents.get(0); // For demo, update the first document
        if (documentRequest.getDocumentType() != null) {
            document.setDocumentType(documentRequest.getDocumentType());
        }
        if (documentRequest.getFile() != null) {
            MultipartFile file = documentRequest.getFile();
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            try {
                Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
                Files.createDirectories(uploadPath);
                Files.deleteIfExists(Paths.get(document.getFilePath()));
                Path targetLocation = uploadPath.resolve(uniqueFileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                document.setFileName(fileName);
                document.setFilePath(targetLocation.toString());
                document.setFileSize(file.getSize());
                document.setMimeType(file.getContentType());
            } catch (IOException ex) {
                throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
            }
        }
        if (documentRequest.getDescription() != null) {
            document.setDescription(documentRequest.getDescription());
        }
        if (documentRequest.getExpiryDate() != null) {
            document.setExpiryDate(documentRequest.getExpiryDate());
        }
        return mapToResponse(documentRepository.save(document));
    }

    @Override
    public void deleteDocumentByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Document> documents = documentRepository.findByEmployeeId(employee.getId());
        for (Document document : documents) {
            try {
                Files.deleteIfExists(Paths.get(document.getFilePath()));
            } catch (IOException ex) {
                throw new BadRequestException("Could not delete file. Please try again!");
            }
            documentRepository.deleteById(document.getId());
        }
    }

    @Override
    public DocumentResponse getDocumentByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Document> documents = documentRepository.findByEmployeeId(employee.getId());
        if (documents.isEmpty()) {
            throw new ResourceNotFoundException("No documents found for employee");
        }
        return mapToResponse(documents.get(0)); // For demo, return the first document
    }

    @Override
    public List<DocumentResponse> getDocumentsByEmployee(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return documentRepository.findByEmployeeId(employee.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public byte[] downloadDocumentByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Document> documents = documentRepository.findByEmployeeId(employee.getId());
        if (documents.isEmpty()) {
            throw new ResourceNotFoundException("No documents found for employee");
        }
        Document document = documents.get(0); // For demo, download the first document
        try {
            Path filePath = Paths.get(document.getFilePath());
            return Files.readAllBytes(filePath);
        } catch (IOException ex) {
            throw new BadRequestException("Could not download file. Please try again!");
        }
    }

    @Override
    public DocumentResponse updateDocumentStatusByEmployeeId(String employeeId, String status) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Document> documents = documentRepository.findByEmployeeId(employee.getId());
        if (documents.isEmpty()) {
            throw new ResourceNotFoundException("No documents found for employee");
        }
        Document document = documents.get(0); // For demo, update the first document
        document.setStatus(status);
        return mapToResponse(documentRepository.save(document));
    }

    private DocumentResponse mapToResponse(Document document) {
        DocumentResponse response = new DocumentResponse();
        response.setId(document.getId());
        response.setEmployeeId(document.getEmployee().getEmployeeId());
        response.setEmployeeName(document.getEmployee().getFirstName() + " " + document.getEmployee().getLastName());
        response.setDocumentType(document.getDocumentType());
        response.setFileName(document.getFileName());
        response.setFilePath(document.getFilePath());
        response.setFileSize(document.getFileSize());
        response.setMimeType(document.getMimeType());
        response.setDescription(document.getDescription());
        response.setStatus(document.getStatus());
        response.setExpiryDate(document.getExpiryDate());
        response.setCreatedAt(document.getCreatedAt());
        response.setUpdatedAt(document.getUpdatedAt());
        return response;
    }
}