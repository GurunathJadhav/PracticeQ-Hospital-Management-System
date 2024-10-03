package com.practiceq.service;

import com.practiceq.payload.ServiceDto;
import com.practiceq.payload.ServiceWithPatient;

public interface ServicesService {

    ServiceWithPatient createServices(ServiceDto serviceDto);
}
