package com.practiceQ.service.Impl;

import com.practiceQ.entity.Appointments;
import com.practiceQ.entity.Patient;

import com.practiceQ.exception.AppointmentNotFoundException;
import com.practiceQ.exception.DoctorNotFoundException;

import com.practiceQ.exception.PatientNotFoundException;
import com.practiceQ.payload.AppointmentsDto;
import com.practiceQ.repository.PatientRepository;
import com.practiceQ.repository.AppointmentsRepository;
import com.practiceQ.repository.DoctorRepository;

import com.practiceQ.service.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentsServiceImpl implements AppointmentsService {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Appointments createAppointments(AppointmentsDto appointmentsDto) {

        Appointments appointments = new Appointments();
        appointments.setAppId(UUID.randomUUID().toString());
        appointments.setDate(appointmentsDto.getDate());
        appointments.setTime(appointmentsDto.getTime());
        appointments.setReason(appointmentsDto.getReason());
        appointments.setEmergency(appointmentsDto.getEmergency());
        appointments.setDoctor(doctorRepository.findById(appointmentsDto.getDoctorId())
                .orElseThrow(()-> new DoctorNotFoundException("Doctor not found for id : "+(appointmentsDto.getDoctorId()))));
        appointments.setPatient(patientRepository.findById(appointmentsDto.getPatientId())
                .orElseThrow(()-> new PatientNotFoundException("Patient not found for id "+appointmentsDto.getPatientId())));
        return appointmentsRepository.save(appointments);

    }

    @Override
    public Appointments updateAppointments(Appointments appointmentsDto) {
        Patient patient = patientRepository.findById(appointmentsDto.getPatient().getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found "));

        List<Appointments> appointments = appointmentsRepository.findAllByPatient(patient);

        Appointments updatedAppointment = appointments.stream()
                .filter(appointment -> appointmentsDto.getAppId().equals(appointment.getAppId()))
                .findFirst()
                .map(appointment -> {
                    appointment.setDate(appointmentsDto.getDate());
                    appointment.setTime(appointmentsDto.getTime());
                    appointment.setReason(appointmentsDto.getReason());
                    appointment.setEmergency(appointmentsDto.getEmergency());
                    appointment.setDoctor(doctorRepository.findById(appointmentsDto.getDoctor().getDoctorId())
                            .orElseThrow(() -> new DoctorNotFoundException("Doctor not found ")));
                    appointment.setPatient(patient);
                    return appointmentsRepository.save(appointment);
                })
                .orElseThrow(()-> new AppointmentNotFoundException("Appointment not found for id "));

        return updatedAppointment;
    }

    @Override
    public Appointments getByPatient(String patientId, String appId) {
        Appointments appointments=new Appointments();
        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new PatientNotFoundException("Patient Not Found"));

        List<Appointments> allByPatient = appointmentsRepository.findAllByPatient(patient);
        allByPatient.stream().map(appointment ->{
            if(appointment.getAppId().equals(appId)){
                appointments.setAppId(appointment.getAppId());
                appointments.setDate(appointment.getDate());
                appointments.setTime(appointment.getTime());
                appointments.setReason(appointment.getReason());
                appointments.setEmergency(appointment.getEmergency());
                appointments.setDoctor(doctorRepository.findById(appointment.getDoctor().getDoctorId()).orElseThrow(()->
                        new DoctorNotFoundException("Doctor not found")));
                appointments.setPatient(patient);
                return appointments;
            }else{
                return null;
            }
        } ).collect(Collectors.toList());
        return appointments;
    }

}
