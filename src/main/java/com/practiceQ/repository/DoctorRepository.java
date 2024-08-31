package com.practiceQ.repository;

import com.practiceQ.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, String> {

    @Query("select d from Doctor d join Department dept on d.department.departmentId = dept.departmentId where d.firstName like %:search% or d.specialization like %:search% or dept.departmentName like %:search%")
    Page<Doctor> searchDoctor(@Param("search") String search, Pageable pageable);


    boolean existsByEmail(String email);

    Optional<Doctor> findByFirstName(String username);
}