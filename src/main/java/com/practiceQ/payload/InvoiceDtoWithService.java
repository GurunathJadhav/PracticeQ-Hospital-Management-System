package com.practiceQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDtoWithService {


    private String invoiceId;

    private String date;

    private Double totalAmount;

    private PatientDto patientDto;
    List<ServiceListForInvoice> serviceListForInvoices;
}
