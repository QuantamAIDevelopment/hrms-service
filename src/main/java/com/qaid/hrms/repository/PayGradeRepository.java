package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.PayGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayGradeRepository extends JpaRepository<PayGrade, Long> {
    Optional<PayGrade> findByName(String name);
    boolean existsByName(String name);
    @Query("SELECT pg FROM PayGrade pg WHERE pg.id = ?1")
    Optional<PayGrade> findById(long id);
} 