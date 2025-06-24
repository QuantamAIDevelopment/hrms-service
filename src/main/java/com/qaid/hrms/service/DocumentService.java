package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.DocumentRequest;
import com.qaid.hrms.model.dto.response.DocumentResponse;

import java.util.List;

public interface DocumentService {
    DocumentResponse uploadDocument(DocumentRequest documentRequest);
    DocumentResponse updateDocument(Long id, DocumentRequest documentRequest);
    void deleteDocument(Long id);
    DocumentResponse getDocumentById(Long id);
    List<DocumentResponse> getAllDocuments();
    List<DocumentResponse> getDocumentsByEmployee(Long employeeId);
    List<DocumentResponse> getDocumentsByType(String documentType);
    byte[] downloadDocument(Long id);
    DocumentResponse updateDocumentStatus(Long id, String status);
} 