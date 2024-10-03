package com.practiceq.service.impl;

import com.practiceq.entity.Admin;
import com.practiceq.entity.Invoice;
import com.practiceq.entity.Patient;
import com.practiceq.entity.Services;
import com.practiceq.exception.AdminNotFoundException;
import com.practiceq.exception.InvoiceNotFoundException;
import com.practiceq.exception.PatientNotFoundException;
import com.practiceq.payload.InvoiceDtoWithService;
import com.practiceq.payload.PatientDto;
import com.practiceq.payload.ServiceListForInvoice;
import com.practiceq.payload.SignInDto;
import com.practiceq.repository.AdminRepository;
import com.practiceq.repository.InvoiceRepository;
import com.practiceq.repository.PatientRepository;
import com.practiceq.repository.ServicesRepository;
import com.practiceq.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JWTService  jwtService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private PdfService pdfService;



    @Autowired
    private EmailService emailService;

    @Override
    public Admin signUp(Admin admin) {
        admin.setAdminId(UUID.randomUUID().toString());
        admin.setPassword(BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt(10)));
        return adminRepository.save(admin);
    }

    @Override
    public boolean existsByRole(String role) {
        return adminRepository.existsByRole(role);
    }

    @Override
    public String verifyLogin(SignInDto signInDto) {
        Admin admin = adminRepository.findByFirstName(signInDto.getUsername()).orElseThrow(() ->
                new AdminNotFoundException("Admin not found"));
        if(BCrypt.checkpw(signInDto.getPassword(),admin.getPassword())){
            return jwtService.generateTokenForAdmin(admin);
        }
        return null;
    }

    @Override
    public String generateInvoice(String patientId) throws IOException {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new PatientNotFoundException("Patient not found "));

        Invoice invoice = invoiceRepository.findByPatient(patient).orElseThrow(() ->
                new InvoiceNotFoundException("Invoice not found "));

        InvoiceDtoWithService invo = new InvoiceDtoWithService();

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

        invo.setServiceListForInvoices(listOfServices);


        PatientDto patientDto=new PatientDto();

        patientDto.setPatientId(patient.getPatientId());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setEmail(patient.getEmail());
        patientDto.setMobile(patient.getMobile());
        invo.setPatientDto(patientDto);

        String path="C://Users//User//Desktop";
        boolean status = pdfService.generatePdf(path + "//" + patient.getFirstName() + " " + patient.getLastName()+".pdf", invo);

        if(status){
            return "Invoice generated successfully";
        }else {
            return "Something went wrong";
        }

    }


}
