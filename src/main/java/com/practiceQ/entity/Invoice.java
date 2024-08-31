package com.practiceQ.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @Column(name = "invoice_id", nullable = false)
    private String invoiceId;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "patient_patient_id")
    private Patient patient;


}