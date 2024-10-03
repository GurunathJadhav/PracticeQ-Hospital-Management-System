package com.practiceq.service;

import com.practiceq.entity.Appointments;
import com.practiceq.payload.AppointmentsDto;

public interface AppointmentsService {

    Appointments createAppointments(AppointmentsDto appointmentsDto);
    Appointments updateAppointments(Appointments appointmentsDto);
    Appointments getByPatient(String patientId,String appId);

}
