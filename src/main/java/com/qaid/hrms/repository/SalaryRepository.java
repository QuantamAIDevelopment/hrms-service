package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findByEmployeeId(Long employeeId);
    List<Salary> findByPayPeriodStartGreaterThanEqualAndPayPeriodEndLessThanEqual(LocalDate startDate, LocalDate endDate);
    List<Salary> findByEmployeeIdAndPayPeriodStartGreaterThanEqualAndPayPeriodEndLessThanEqual(
            Long employeeId, LocalDate startDate, LocalDate endDate);
            
    boolean existsByEmployeeIdAndPayPeriodStartAndPayPeriodEnd(Long employeeId, LocalDate startDate, LocalDate endDate);
    
    List<Salary> findByPayPeriodStartBetweenOrPayPeriodEndBetween(
            LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2);
            
    List<Salary> findByEmployeeIdAndPayPeriodStartBetweenOrPayPeriodEndBetween(
            Long employeeId, LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2);
}