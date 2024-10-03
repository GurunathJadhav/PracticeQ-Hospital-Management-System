package com.practiceq.service.impl;

import com.practiceq.entity.AcceptAppointment;


import com.practiceq.exception.AppointmentNotFoundException;
import com.practiceq.exception.DoctorNotFoundException;
import com.practiceq.payload.AcceptAppointmentDto;
import com.practiceq.repository.AcceptAppointmentRepository;
import com.practiceq.repository.AppointmentsRepository;
import com.practiceq.repository.DoctorRepository;
import com.practiceq.service.AcceptAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AcceptAppointmentServiceImpl implements AcceptAppointmentService {

    @Autowired
    private AcceptAppointmentRepository acceptAppointmentRepository;

    @Autowired
    private AppointmentsRepository appointmentsRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public AcceptAppointment acceptAppointment(AcceptAppointmentDto acceptAppointmentDto) {
        AcceptAppointment acceptAppointment=new AcceptAppointment();
        acceptAppointment.setAcapId(UUID.randomUUID().toString());
        acceptAppointment.setAppointments(appointmentsRepository.findById(acceptAppointmentDto.getAppointmentId())
                .orElseThrow(()-> new AppointmentNotFoundException("Appointment not found")));
        acceptAppointment.setDoctor(doctorRepository.findById(acceptAppointmentDto.getDoctorId())
                .orElseThrow(()-> new DoctorNotFoundException("Doctor not found")));

        return acceptAppointmentRepository.save(acceptAppointment);
    }
}
