package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.PerformanceReview;
import com.qaid.hrms.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {

    List<PerformanceReview> findByEmployee(Employee employee);
}
