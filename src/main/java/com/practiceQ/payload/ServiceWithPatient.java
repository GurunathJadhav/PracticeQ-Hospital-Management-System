package com.practiceQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceWithPatient {
    private String serviceId;

    private String serviceName;

    private Double cost;

    private InvoiceWithPatientDto invoiceWithPatient;
}