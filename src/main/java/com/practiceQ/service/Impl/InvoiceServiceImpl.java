package com.practiceQ.service.Impl;

import com.practiceQ.entity.Invoice;
import com.practiceQ.entity.Patient;
import com.practiceQ.entity.Services;

import com.practiceQ.exception.InvoiceNotFoundException;
import com.practiceQ.exception.PatientNotFoundException;
import com.practiceQ.payload.*;
import com.practiceQ.repository.InvoiceRepository;
import com.practiceQ.repository.PatientRepository;
import com.practiceQ.repository.ServicesRepository;
import com.practiceQ.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ServicesRepository servicesRepository;
    @Override
    public InvoiceWithPatientDto createInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(UUID.randomUUID().toString());

        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String formattedDate = currentDate.format(dateFormatter);

        invoice.setDate(formattedDate);
        invoice.setTotalAmount(invoiceDto.getTotalAmount());
        invoice.setPatient(patientRepository.findById(invoiceDto.getPatientId())
                .orElseThrow(()-> new PatientNotFoundException("Patient not found for id "+invoiceDto.getPatientId())));

        Invoice saved = invoiceRepository.save(invoice);

        InvoiceWithPatientDto invoiceWithPatientDto=new InvoiceWithPatientDto();
        invoiceWithPatientDto.setInvoiceId(saved.getInvoiceId());
        invoiceWithPatientDto.setDate(saved.getDate());
        invoiceWithPatientDto.setTotalAmount(saved.getTotalAmount());

        Patient patient = patientRepository.findById(saved.getPatient().getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for id " + invoiceDto.getPatientId()));

        PatientDto patientDto=new PatientDto();
        patientDto.setPatientId(patient.getPatientId());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setEmail(patient.getEmail());
        patientDto.setMobile(patient.getMobile());

        invoiceWithPatientDto.setPatientDto(patientDto);

        return invoiceWithPatientDto;

    }

    @Override
    public InvoiceDtoWithService getAll(String invoiceId) {
        InvoiceDtoWithService invo = new InvoiceDtoWithService();



        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(()-> new InvoiceNotFoundException("Invoice not found for id "+ invoiceId));
        invo.setInvoiceId(invoice.getInvoiceId());
        invo.setDate(invoice.getDate());
        invo.setTotalAmount(invoice.getTotalAmount());

        List<Services> allServices = servicesRepository.findAllByInvoice(invoice);

        List<ServiceListForInvoice> listOfServices = allServices.stream().map(services -> {
            ServiceListForInvoice serviceListForInvoice = new ServiceListForInvoice();
            serviceListForInvoice.setServiceId(services.getServiceId());
            serviceListForInvoice.setServiceName(services.getServiceName());
            serviceListForInvoice.setCost(services.getCost());


            return serviceListForInvoice;
        }).collect(Collectors.toList());

        double totalServiceCost = allServices.stream()
                .mapToDouble(Services::getCost)
                .sum();




        double updatedTotalAmount = invoice.getTotalAmount() + totalServiceCost;
        invo.setTotalAmount(totalServiceCost);

        invoice.setTotalAmount(totalServiceCost);
        invoice.setInvoiceId(invoice.getInvoiceId());
        invoiceRepository.save(invoice);

        PatientDto patientDto=new PatientDto();
        Patient patient = patientRepository.findById(invoice.getPatient().getPatientId())
                .orElseThrow(()-> new PatientNotFoundException("Patient not found for id "+invoice.getPatient().getPatientId()));
        patientDto.setPatientId(patient.getPatientId());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setEmail(patient.getEmail());
        patientDto.setMobile(patient.getMobile());
        invo.setPatientDto(patientDto);

        invo.setServiceListForInvoices(listOfServices);

        return invo;
    }
}
