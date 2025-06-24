package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.model.entity.LeaveBalance;
import com.qaid.hrms.model.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

    List<LeaveBalance> findByEmployee(Employee employee);
    
    Optional<LeaveBalance> findByEmployeeAndLeaveTypeAndYear(
        Employee employee,
        LeaveType leaveType,
        Integer year
    );
    
    @Query("SELECT lb FROM LeaveBalance lb WHERE lb.employee = :employee AND lb.year = :year AND lb.remainingDays > 0")
    List<LeaveBalance> findAvailableLeaveBalances(
        @Param("employee") Employee employee,
        @Param("year") Integer year
    );
    
    @Query("SELECT SUM(lb.usedDays) FROM LeaveBalance lb WHERE lb.employee = :employee AND lb.leaveType = :leaveType AND lb.year = :year")
    Integer getTotalUsedDays(
        @Param("employee") Employee employee,
        @Param("leaveType") LeaveType leaveType,
        @Param("year") Integer year
    );
    
    @Query("SELECT lb FROM LeaveBalance lb WHERE lb.employee.department.id = :departmentId AND lb.year = :year")
    List<LeaveBalance> findDepartmentLeaveBalances(
        @Param("departmentId") Long departmentId,
        @Param("year") Integer year
    );

    List<LeaveBalance> findByEmployeeAndYear(Employee employee, Integer year);
} 