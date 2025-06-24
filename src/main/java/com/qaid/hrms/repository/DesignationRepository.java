package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.Department;
import com.qaid.hrms.model.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
    Optional<Designation> findByTitle(String title);
    Optional<Designation> findByCode(String code);
    List<Designation> findByIsActive(Boolean isActive);
    
    @Query("SELECT d FROM Designation d WHERE d.department.id = :departmentId")
    List<Designation> findByDepartment(@Param("departmentId") Long departmentId);
    
    @Query("SELECT d FROM Designation d WHERE d.level = :level AND d.isActive = true")
    List<Designation> findActiveByLevel(@Param("level") String level);
    
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.designation.id = :designationId")
    long countEmployeesWithDesignation(@Param("designationId") Long designationId);
    
    @Query("SELECT d FROM Designation d WHERE d.department = :department AND d.level = :level")
    List<Designation> findByDepartmentAndLevel(
        @Param("department") Department department,
        @Param("level") String level
    );
    
    boolean existsByCode(String code);
    
    @Query("SELECT d FROM Designation d WHERE d.department.id = :departmentId AND d.isActive = true ORDER BY d.level")
    List<Designation> findActiveDesignationsByDepartmentOrderByLevel(@Param("departmentId") Long departmentId);
    
    @Query("SELECT COUNT(d) FROM Designation d WHERE d.department.id = ?1")
    long countByDepartment(Long departmentId);
} 