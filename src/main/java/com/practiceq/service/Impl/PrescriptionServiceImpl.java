package com.practiceq.service.Impl;

import com.practiceq.entity.Prescription;
import com.practiceq.exception.DoctorNotFoundException;
import com.practiceq.exception.PatientNotFoundException;
import com.practiceq.payload.*;
import com.practiceq.repository.DoctorRepository;

import com.practiceq.repository.PatientRepository;
import com.practiceq.repository.PrescriptionRepository;
import com.practiceq.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    @Override
    public PrescriptionResponse createPrescription(PrescriptionDto prescriptionDto) {
        Prescription prescription = new Prescription();

        prescription.setPrescriptionId(UUID.randomUUID().toString());
        prescription.setMedicines(prescriptionDto.getMedicines());
        prescription.setDuration(prescriptionDto.getDuration());
        prescription.setPatient(patientRepository.findById(prescriptionDto.getPatientId()).orElseThrow(()-> new PatientNotFoundException("Patient not found")));
        prescription.setDoctor(doctorRepository.findById(prescriptionDto.getDoctorId()).orElseThrow(()-> new DoctorNotFoundException("Doctor not found")));

        Prescription saved = prescriptionRepository.save(prescription);
//
        PrescriptionResponse prescriptionResponse=new PrescriptionResponse();

        prescriptionResponse.setPrescriptionId(saved.getPrescriptionId());
        prescriptionResponse.setMedicines(saved.getMedicines());
        prescriptionResponse.setDuration(saved.getDuration());

        prescriptionResponse.setDoctor(saved.getDoctor());
        prescriptionResponse.setPatient(saved.getPatient());

        return prescriptionResponse;
    }
}
