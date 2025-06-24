package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByEmployeeId(Long employeeId);
    List<Document> findByDocumentType(String documentType);
    List<Document> findByEmployeeIdAndDocumentType(Long employeeId, String documentType);
}