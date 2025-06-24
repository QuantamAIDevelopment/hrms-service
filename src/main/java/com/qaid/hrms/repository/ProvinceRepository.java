package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Optional<Province> findByName(String name);
    Optional<Province> findByCode(String code);
    boolean existsByName(String name);
    boolean existsByCode(String code);
    List<Province> findByCountryId(Long countryId);
    @Query("SELECT p FROM Province p WHERE p.id = ?1")
    Optional<Province> findById(long id);
} 