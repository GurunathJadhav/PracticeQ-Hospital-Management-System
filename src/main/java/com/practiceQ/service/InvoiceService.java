package com.practiceQ.service;

import com.practiceQ.entity.Invoice;
import com.practiceQ.payload.InvoiceDto;
import com.practiceQ.payload.InvoiceDtoWithService;
import com.practiceQ.payload.InvoiceWithPatientDto;

public interface InvoiceService {

    InvoiceWithPatientDto createInvoice(InvoiceDto invoiceDto);

    InvoiceDtoWithService getAll(String invoiceId);
}
