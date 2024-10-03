package com.practiceq.repository;

import com.practiceq.entity.Invoice;
import com.practiceq.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findAllByPatient(Patient patient);

    Optional<Invoice> findByPatient(Patient patient);
}