package com.practiceQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceWithService {
    private String date;
    private Double totalAmount;

    List<ServiceListForInvoice> serviceListForInvoiceList;
}
