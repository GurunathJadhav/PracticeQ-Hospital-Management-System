package com.practiceq.repository;

import com.practiceq.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    boolean existsByRole(String role);

    Optional<Admin> findByFirstName(String username);
}