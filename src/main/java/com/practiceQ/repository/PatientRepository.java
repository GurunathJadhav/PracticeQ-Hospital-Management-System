package com.practiceQ.repository;

import com.practiceQ.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, String> {
    boolean existsByEmail(String email);

    Optional<Patient> findByFirstName(String username);
}
