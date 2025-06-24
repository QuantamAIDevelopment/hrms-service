package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
    Optional<Department> findByCode(String code);
    List<Department> findByIsActive(Boolean isActive);
    
    @Query("SELECT d FROM Department d WHERE d.parentDepartment IS NULL")
    List<Department> findRootDepartments();
    
    @Query("SELECT d FROM Department d WHERE d.parentDepartment.id = :parentId")
    List<Department> findSubDepartments(@Param("parentId") Long parentId);
    
    @Query("SELECT d FROM Department d WHERE d.manager.id = :managerId")
    List<Department> findDepartmentsByManager(@Param("managerId") Long managerId);
    
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.id = :departmentId")
    long countEmployeesInDepartment(@Param("departmentId") Long departmentId);
    
    @Query("SELECT d FROM Department d WHERE d.location = :location AND d.isActive = true")
    List<Department> findActiveDepartmentsByLocation(@Param("location") String location);
    
    boolean existsByCode(String code);
    
    @Query("SELECT COUNT(d) FROM Department d WHERE d.parentDepartment.id = ?1")
    long countSubDepartments(Long parentDepartmentId);
    
    long countByIsActive(boolean isActive);
    
    Page<Department> findByIsActive(boolean isActive, Pageable pageable);
} 