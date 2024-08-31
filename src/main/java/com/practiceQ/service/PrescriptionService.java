package com.practiceQ.service;

import com.practiceQ.entity.Prescription;
import com.practiceQ.payload.PrescriptionDto;
import com.practiceQ.payload.PrescriptionResponse;

public interface PrescriptionService {

    PrescriptionResponse createPrescription(PrescriptionDto prescriptionDto);
}
