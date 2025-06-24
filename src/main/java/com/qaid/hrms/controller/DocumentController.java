package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.DocumentRequest;
import com.qaid.hrms.model.dto.response.DocumentResponse;
import com.qaid.hrms.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @PostMapping
    public ResponseEntity<DocumentResponse> uploadDocument(@RequestBody DocumentRequest request) {
        return ResponseEntity.ok(documentService.uploadDocument(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/{employeeId}")
    public ResponseEntity<DocumentResponse> updateDocumentByEmployeeId(@PathVariable String employeeId, @RequestBody DocumentRequest request) {
        return ResponseEntity.ok(documentService.updateDocumentByEmployeeId(employeeId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteDocumentByEmployeeId(@PathVariable String employeeId) {
        documentService.deleteDocumentByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/{employeeId}")
    public ResponseEntity<DocumentResponse> getDocumentByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(documentService.getDocumentByEmployeeId(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<DocumentResponse>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<DocumentResponse>> getDocumentsByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(documentService.getDocumentsByEmployee(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/type/{documentType}")
    public ResponseEntity<List<DocumentResponse>> getDocumentsByType(@PathVariable String documentType) {
        return ResponseEntity.ok(documentService.getDocumentsByType(documentType));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/download/{employeeId}")
    public ResponseEntity<byte[]> downloadDocumentByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(documentService.downloadDocumentByEmployeeId(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/{employeeId}/status")
    public ResponseEntity<DocumentResponse> updateDocumentStatusByEmployeeId(@PathVariable String employeeId, @RequestParam String status) {
        return ResponseEntity.ok(documentService.updateDocumentStatusByEmployeeId(employeeId, status));
    }
}
