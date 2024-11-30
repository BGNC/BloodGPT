package com.bgnc.bloodgpt.repository;

import com.bgnc.bloodgpt.model.City;
import com.bgnc.bloodgpt.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<Hospital> findByName(String name);

}
