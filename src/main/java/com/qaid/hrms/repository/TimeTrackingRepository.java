package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.TimeTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeTrackingRepository extends JpaRepository<TimeTracking, Long> {

    List<TimeTracking> findByEmployeeId(Long employeeId);
}
