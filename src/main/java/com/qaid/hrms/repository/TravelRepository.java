package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {
    List<Travel> findByEmployeeId(Long employeeId);
    List<Travel> findByStartDateBetweenOrEndDateBetween(LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2);
    List<Travel> findByEmployeeIdAndStartDateBetweenOrEndDateBetween(
            Long employeeId, LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2);
}