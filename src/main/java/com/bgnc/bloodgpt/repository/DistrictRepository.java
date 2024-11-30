package com.bgnc.bloodgpt.repository;

import com.bgnc.bloodgpt.model.District;
import com.bgnc.bloodgpt.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District,Long> {
    Optional<Hospital> findByName(String name);
}
