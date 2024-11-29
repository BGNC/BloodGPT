package com.bgnc.bloodgpt.repository;

import com.bgnc.bloodgpt.model.User;
import com.bgnc.bloodgpt.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser_TcNumber(String tcNumber);
}
