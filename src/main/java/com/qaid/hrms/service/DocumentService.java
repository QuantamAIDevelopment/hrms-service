package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.DocumentRequest;
import com.qaid.hrms.model.dto.response.DocumentResponse;

import java.util.List;

public interface DocumentService {
    DocumentResponse uploadDocument(DocumentRequest documentRequest);
    DocumentResponse updateDocumentByEmployeeId(String employeeId, DocumentRequest documentRequest);
    void deleteDocumentByEmployeeId(String employeeId);
    DocumentResponse getDocumentByEmployeeId(String employeeId);
    List<DocumentResponse> getAllDocuments();
    List<DocumentResponse> getDocumentsByEmployee(String employeeId);
    List<DocumentResponse> getDocumentsByType(String documentType);
    byte[] downloadDocumentByEmployeeId(String employeeId);
    DocumentResponse updateDocumentStatusByEmployeeId(String employeeId, String status);
}