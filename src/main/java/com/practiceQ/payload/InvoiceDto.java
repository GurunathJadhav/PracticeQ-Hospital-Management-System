package com.practiceQ.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {

    private String invoiceId;


    private Double totalAmount;

    @NotEmpty(message = "Patient Id must not be empty")
    private String patientId;
}
