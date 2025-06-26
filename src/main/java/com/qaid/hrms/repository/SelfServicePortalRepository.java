package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.SelfServicePortal;
import com.qaid.hrms.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelfServicePortalRepository extends JpaRepository<SelfServicePortal, Long> {

    List<SelfServicePortal> findByEmployee(Employee employee);
}
