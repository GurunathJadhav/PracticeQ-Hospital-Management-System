package com.practiceq.service;

import com.practiceq.payload.PrescriptionDto;
import com.practiceq.payload.PrescriptionResponse;

public interface PrescriptionService {

    PrescriptionResponse createPrescription(PrescriptionDto prescriptionDto);
}
