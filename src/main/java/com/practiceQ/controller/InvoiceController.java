package com.practiceQ.controller;

import com.practiceQ.entity.Invoice;
import com.practiceQ.payload.InvoiceDto;
import com.practiceQ.payload.InvoiceDtoWithService;
import com.practiceQ.payload.InvoiceWithPatientDto;
import com.practiceQ.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<?> createInvoice(@Valid @RequestBody InvoiceDto invoiceDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult
                    .getFieldError()
                    .getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        InvoiceWithPatientDto invoice = invoiceService.createInvoice(invoiceDto);
        return new ResponseEntity<>(invoice, HttpStatus.CREATED);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDtoWithService> getAll(@PathVariable String invoiceId){
        InvoiceDtoWithService all = invoiceService.getAll(invoiceId);
        return new ResponseEntity<>(all,HttpStatus.OK);
    }
}
