package com.bgnc.bloodgpt.repository;

import com.bgnc.bloodgpt.model.Doctor;
import com.bgnc.bloodgpt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser(User user);
    Optional<Doctor> findByUser_TcNumber(String tcNumber);
}
