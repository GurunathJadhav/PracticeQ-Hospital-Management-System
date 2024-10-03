package com.practiceq.service.Impl;



import com.practiceq.entity.Invoice;
import com.practiceq.entity.Patient;

import com.practiceq.entity.Services;
import com.practiceq.exception.InvoiceNotFoundException;
import com.practiceq.exception.PatientNotFoundException;
import com.practiceq.payload.*;
import com.practiceq.repository.InvoiceRepository;

import com.practiceq.repository.PatientRepository;
import com.practiceq.repository.ServicesRepository;
import com.practiceq.service.ServicesService;
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
