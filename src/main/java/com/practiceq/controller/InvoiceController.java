package com.practiceq.controller;

import com.practiceq.payload.InvoiceDto;
import com.practiceq.payload.InvoiceDtoWithService;
import com.practiceq.payload.InvoiceWithPatientDto;
import com.practiceq.service.InvoiceService;
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
