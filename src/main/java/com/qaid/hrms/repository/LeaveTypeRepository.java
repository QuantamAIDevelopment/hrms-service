package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    Optional<LeaveType> findByName(String name);
    List<LeaveType> findByIsActive(Boolean isActive);
    
    @Query("SELECT lt FROM LeaveType lt WHERE lt.isActive = true AND lt.requiresApproval = true")
    List<LeaveType> findAllActiveApprovalRequired();
    
    boolean existsByName(String name);
    
    @Query("SELECT COUNT(lt) FROM LeaveType lt WHERE lt.isActive = true")
    long countActiveLeaveTypes();
} 