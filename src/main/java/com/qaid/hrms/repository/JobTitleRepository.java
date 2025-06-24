package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
    Optional<JobTitle> findByName(String name);
    boolean existsByName(String name);
    @Query("SELECT j FROM JobTitle j where j.id = ?1")
    Optional<JobTitle> findById(long id);
} 