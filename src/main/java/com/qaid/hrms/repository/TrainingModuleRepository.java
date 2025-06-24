package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.TrainingModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingModuleRepository extends JpaRepository<TrainingModule, Long> {
}
