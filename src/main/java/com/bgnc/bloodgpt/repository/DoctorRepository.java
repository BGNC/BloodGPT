package com.bgnc.bloodgpt.repository;

import com.bgnc.bloodgpt.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser_TcNumber(String tcNumber);
}
