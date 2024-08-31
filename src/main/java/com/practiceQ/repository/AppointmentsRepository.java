package com.practiceQ.repository;

import com.practiceQ.entity.Appointments;
import com.practiceQ.entity.Doctor;
import com.practiceQ.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentsRepository extends JpaRepository<Appointments, String> {

    Page<Appointments> findAllByDoctor(Doctor doctor, Pageable pageable);

    List<Appointments> findAllByPatient(Patient patient);

    @Query("select a from Appointments a join a.patient p where p = :patient and a.date like %:search%")
    List<Appointments> findAllByPatientBySearch(@Param("patient") Patient patient, @Param("search") String search);

    @Query("select a from Appointments a join a.doctor d where d=:doctor and a.emergency =:search")
    Page<Appointments> findAllByDoctorWithSearch(@Param("doctor") Doctor doctor, @Param("search") boolean search, Pageable pageable);
}