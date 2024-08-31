package com.practiceQ.service.Impl;

import com.practiceQ.entity.AcceptAppointment;


import com.practiceQ.exception.AppointmentNotFoundException;
import com.practiceQ.exception.DoctorNotFoundException;
import com.practiceQ.payload.AcceptAppointmentDto;
import com.practiceQ.repository.AcceptAppointmentRepository;
import com.practiceQ.repository.AppointmentsRepository;
import com.practiceQ.repository.DoctorRepository;
import com.practiceQ.service.AcceptAppointmentService;
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
