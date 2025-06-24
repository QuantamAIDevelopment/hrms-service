package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.Department;
import com.qaid.hrms.model.entity.Designation;
import com.qaid.hrms.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeId(String employeeId);
    boolean existsByEmployeeId(String employeeId);
    List<Employee> findByDepartmentId(Long departmentId);
    List<Employee> findBySupervisorId(Long supervisorId);
    Optional<Employee> findByWorkEmail(String workEmail);
    default Optional<Employee> findByUsername(String username) {
        return findByWorkEmail(username);
    }
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.employmentStatus")
    List<Employee> findAllEmployees();
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByDepartmentAndIsActive(Department department, Boolean isActive);
    List<Employee> findByDepartment(Department department);
    Page<Employee> findByDepartment(Department department, Pageable pageable);
    long countByDepartment(Department department);
    long countByDepartmentAndIsActive(Department department, Boolean isActive);
    long countByDesignation(Designation designation);
    long countByDesignationAndIsActive(Designation designation, Boolean isActive);
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.isActive = :isActive")
    long countByIsActive(@Param("isActive") Boolean isActive);
    @Query("SELECT e FROM Employee e WHERE e.department = :department AND e.isActive = true")
    List<Employee> findActiveEmployeesByDepartment(@Param("department") Department department);
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department = :department AND e.isActive = true")
    long countActiveEmployeesByDepartment(@Param("department") Department department);
}