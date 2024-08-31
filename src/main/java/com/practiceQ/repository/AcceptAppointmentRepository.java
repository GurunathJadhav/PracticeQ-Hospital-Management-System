package com.practiceQ.repository;

import com.practiceQ.entity.AcceptAppointment;
import com.practiceQ.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcceptAppointmentRepository extends JpaRepository<AcceptAppointment, String> {
    AcceptAppointment findLast1ByDoctor(Doctor doctor);

    List<AcceptAppointment> findAllByDoctor(Doctor doctor);
}