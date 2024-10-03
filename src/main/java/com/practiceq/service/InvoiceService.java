package com.practiceq.service;

import com.practiceq.payload.InvoiceDto;
import com.practiceq.payload.InvoiceDtoWithService;
import com.practiceq.payload.InvoiceWithPatientDto;

public interface InvoiceService {

    InvoiceWithPatientDto createInvoice(InvoiceDto invoiceDto);

    InvoiceDtoWithService getAll(String invoiceId);
}
