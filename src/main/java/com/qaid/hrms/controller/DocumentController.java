package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.DocumentRequest;
import com.qaid.hrms.model.dto.response.DocumentResponse;
import com.qaid.hrms.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<DocumentResponse> uploadDocument(@RequestBody DocumentRequest request) {
        return ResponseEntity.ok(documentService.uploadDocument(request));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<DocumentResponse> updateDocumentByEmployeeId(@PathVariable String employeeId, @RequestBody DocumentRequest request) {
        return ResponseEntity.ok(documentService.updateDocumentByEmployeeId(employeeId, request));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteDocumentByEmployeeId(@PathVariable String employeeId) {
        documentService.deleteDocumentByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<DocumentResponse> getDocumentByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(documentService.getDocumentByEmployeeId(employeeId));
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<DocumentResponse>> getDocumentsByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(documentService.getDocumentsByEmployee(employeeId));
    }

    @GetMapping("/type/{documentType}")
    public ResponseEntity<List<DocumentResponse>> getDocumentsByType(@PathVariable String documentType) {
        return ResponseEntity.ok(documentService.getDocumentsByType(documentType));
    }

    @GetMapping("/download/{employeeId}")
    public ResponseEntity<byte[]> downloadDocumentByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(documentService.downloadDocumentByEmployeeId(employeeId));
    }

    @PutMapping("/{employeeId}/status")
    public ResponseEntity<DocumentResponse> updateDocumentStatusByEmployeeId(@PathVariable String employeeId, @RequestParam String status) {
        return ResponseEntity.ok(documentService.updateDocumentStatusByEmployeeId(employeeId, status));
    }
}
