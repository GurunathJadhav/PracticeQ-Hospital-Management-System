package com.practiceQ.service;

import com.practiceQ.entity.Services;
import com.practiceQ.payload.InvoiceWithPatientDto;
import com.practiceQ.payload.ServiceDto;
import com.practiceQ.payload.ServiceWithPatient;

public interface ServicesService {

    ServiceWithPatient createServices(ServiceDto serviceDto);
}
