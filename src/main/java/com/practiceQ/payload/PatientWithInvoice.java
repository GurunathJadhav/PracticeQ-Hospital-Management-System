package com.practiceQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientWithInvoice {

    private String firstName;

    private String lastName;

    private String email;
    private Long mobile;

    private List<InvoiceWithService> invoiceWithServiceList;



}
