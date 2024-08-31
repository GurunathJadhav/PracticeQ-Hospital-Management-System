package com.practiceQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceWithPatientDto {
    private String invoiceId;
    private String date;

    private Double totalAmount;

    private PatientDto patientDto;

}
