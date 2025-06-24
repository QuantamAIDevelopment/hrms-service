package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.CompanyStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyStructureRepository extends JpaRepository<CompanyStructure, Long> {
    Optional<CompanyStructure> findByTitle(String title);
    boolean existsByTitle(String title);
    List<CompanyStructure> findByParentId(Long parentId);
    List<CompanyStructure> findByHeadId(Long headId);
    List<CompanyStructure> findByCountryId(Long countryId);
    @Query("SELECT cs FROM CompanyStructure cs WHERE cs.id = ?1")
    Optional<CompanyStructure> findById(long Id);
} 