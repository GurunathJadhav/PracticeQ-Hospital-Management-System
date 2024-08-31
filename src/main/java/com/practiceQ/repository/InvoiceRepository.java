package com.practiceQ.repository;

import com.practiceQ.entity.Invoice;
import com.practiceQ.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findAllByPatient(Patient patient);

    Optional<Invoice> findByPatient(Patient patient);
}