package com.qaid.hrms.repository;

import com.qaid.hrms.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCode(String code);
    Optional<Country> findByName(String name);
    boolean existsByCode(String code);
    boolean existsByName(String name);
    Optional<Country> findById(long id);
} 