package com.practiceq.repository;

import com.practiceq.entity.Invoice;
import com.practiceq.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, String> {
    List<Services> findAllByInvoice(Invoice invoice);
}