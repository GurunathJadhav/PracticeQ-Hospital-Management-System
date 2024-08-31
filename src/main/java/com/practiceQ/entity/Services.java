package com.practiceQ.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "services")
public class Services {
    @Id
    @Column(name = "service_id", nullable = false)
    private String serviceId;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @ManyToOne
    @JoinColumn(name = "invoice_invoice_id")
    private Invoice invoice;

}
