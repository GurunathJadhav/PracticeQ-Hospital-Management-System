package com.practiceq.repository;

import com.practiceq.entity.AcceptAppointment;
import com.practiceq.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcceptAppointmentRepository extends JpaRepository<AcceptAppointment, String> {
    AcceptAppointment findLast1ByDoctor(Doctor doctor);

    List<AcceptAppointment> findAllByDoctor(Doctor doctor);
}