package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.Department;
import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.model.entity.LeaveRequest;
import com.qaid.hrms.model.enums.LeaveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    Page<LeaveRequest> findByEmployee(Employee employee, Pageable pageable);
    
    Page<LeaveRequest> findByEmployeeDepartmentAndStatus(Department department, LeaveStatus status, Pageable pageable);
    
//    List<LeaveRequest> findByEmployeeDepartmentAndDateRange(Department department, LocalDate startDate, LocalDate endDate, LeaveStatus status);
    
    @Query("SELECT COUNT(lr) FROM LeaveRequest lr WHERE lr.employee.department = :department AND lr.status = :status")
    long countByEmployeeDepartmentAndStatus(@Param("department") Department department, @Param("status") LeaveStatus status);
    
    @Query("SELECT COUNT(lr) FROM LeaveRequest lr WHERE lr.employee = :employee AND lr.status = :status")
    long countByEmployeeAndStatus(@Param("employee") Employee employee, @Param("status") LeaveStatus status);
    
    @Query("SELECT EXISTS(SELECT 1 FROM LeaveRequest lr WHERE lr.employee = :employee " +
           "AND lr.status = :status AND " +
           "((lr.startDate BETWEEN :startDate AND :endDate) OR " +
           "(lr.endDate BETWEEN :startDate AND :endDate) OR " +
           "(:startDate BETWEEN lr.startDate AND lr.endDate)))")
    boolean existsOverlappingLeaves(@Param("employee") Employee employee,
                                  @Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate,
                                  @Param("status") LeaveStatus status);
    
    @Query("SELECT lr.leaveType.name as leaveType, COUNT(lr) as count " +
           "FROM LeaveRequest lr " +
           "WHERE lr.employee.department = :department " +
           "AND YEAR(lr.startDate) = :year " +
           "AND lr.status = 'APPROVED' " +
           "GROUP BY lr.leaveType.name")
    List<Map<String, Object>> findLeaveDistributionByType(@Param("department") Department department,
                                                         @Param("year") Integer year);
    
    @Query("SELECT MONTH(lr.startDate) as month, COUNT(lr) as count " +
           "FROM LeaveRequest lr " +
           "WHERE lr.employee.department = :department " +
           "AND YEAR(lr.startDate) = :year " +
           "AND lr.status = 'APPROVED' " +
           "GROUP BY MONTH(lr.startDate)")
    List<Map<String, Object>> findMonthlyLeaveDistribution(@Param("department") Department department,
                                                          @Param("year") Integer year);
    
    @Query("SELECT MONTH(lr.startDate) as month, COUNT(lr) as count " +
           "FROM LeaveRequest lr " +
           "WHERE lr.employee = :employee " +
           "AND YEAR(lr.startDate) = :year " +
           "AND lr.status = 'APPROVED' " +
           "GROUP BY MONTH(lr.startDate)")
    List<Map<String, Object>> findMonthlyLeaveDistribution(@Param("employee") Employee employee,
                                                          @Param("year") Integer year);

    List<LeaveRequest> findByEmployeeId(Long employeeId);
    
    List<LeaveRequest> findByEmployeeIdAndStatus(Long employeeId, LeaveStatus status);
    
    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.employee.department = :department " +
           "AND lr.startDate <= :endDate AND lr.endDate >= :startDate " +
           "AND lr.status = :status")
    List<LeaveRequest> findByEmployeeDepartmentAndDateRange(
        @Param("department") Department department,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("status") LeaveStatus status
    );
    
    Optional<LeaveRequest> findByIdAndEmployeeId(Long id, Long employeeId);
    
    List<LeaveRequest> findByStatus(LeaveStatus status);
    
    @Query("SELECT COUNT(lr) FROM LeaveRequest lr WHERE lr.employee.department = :department " +
           "AND lr.startDate <= :endDate AND lr.endDate >= :startDate " +
           "AND lr.status = :status")
    long countByDepartmentAndDateRange(
        @Param("department") Department department,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("status") LeaveStatus status
    );
} 