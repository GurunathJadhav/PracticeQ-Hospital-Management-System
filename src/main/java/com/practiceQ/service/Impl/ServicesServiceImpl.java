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
import com.practiceQ.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServicesServiceImpl implements ServicesService {

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PatientRepository patientRepository;
    @Override
    public ServiceWithPatient createServices(ServiceDto serviceDto) {

        Services service=new Services();
        service.setServiceId(UUID.randomUUID().toString());
        service.setServiceName(serviceDto.getServiceName());
        service.setCost(serviceDto.getCost());
        Invoice invoice = invoiceRepository.findById(serviceDto.getInvoiceId())
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found for id " + serviceDto.getInvoiceId()));

        invoice.setInvoiceId(serviceDto.getInvoiceId());
        invoice.setTotalAmount(invoice.getTotalAmount()+serviceDto.getCost());
        Invoice save = invoiceRepository.save(invoice);
        service.setInvoice(save);

        Services savedService = servicesRepository.save(service);
        ServiceWithPatient serviceWithPatient =new ServiceWithPatient();
        serviceWithPatient.setServiceId(savedService.getServiceId());
        serviceWithPatient.setServiceName(savedService.getServiceName());
        serviceWithPatient.setCost(savedService.getCost());

        InvoiceWithPatientDto invoiceWithPatientDto=new InvoiceWithPatientDto();
        invoiceWithPatientDto.setInvoiceId(save.getInvoiceId());
        invoiceWithPatientDto.setDate(save.getDate());
        invoiceWithPatientDto.setTotalAmount(save.getTotalAmount());

        Patient patient = patientRepository.findById(save.getPatient().getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for id "));

        PatientDto patientDto=new PatientDto();
        patientDto.setPatientId(patient.getPatientId());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setEmail(patient.getEmail());
        patientDto.setMobile(patient.getMobile());

        invoiceWithPatientDto.setPatientDto(patientDto);

        serviceWithPatient.setInvoiceWithPatient(invoiceWithPatientDto);

        return serviceWithPatient;
    }
}
