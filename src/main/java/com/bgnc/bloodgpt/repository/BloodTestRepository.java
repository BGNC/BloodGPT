package com.bgnc.bloodgpt.repository;

import com.bgnc.bloodgpt.model.BloodTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloodTestRepository extends JpaRepository<BloodTest,Long> {
    List<BloodTest> findByUser_Id(Long userId);
}
