package com.practiceQ.repository;

import com.practiceQ.entity.Invoice;
import com.practiceQ.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, String> {
    List<Services> findAllByInvoice(Invoice invoice);
}