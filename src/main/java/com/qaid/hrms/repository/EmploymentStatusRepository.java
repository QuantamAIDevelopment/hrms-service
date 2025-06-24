package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.EmploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmploymentStatusRepository extends JpaRepository<EmploymentStatus, Long> {
    Optional<EmploymentStatus> findByName(String name);
    boolean existsByName(String name);
    @Query("SELECT es FROM EmploymentStatus es WHERE es.id = ?1")
    Optional<EmploymentStatus> findById(long id);
} 