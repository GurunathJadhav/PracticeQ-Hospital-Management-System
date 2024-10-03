package com.practiceq.payload;

import com.practiceq.entity.Doctor;
import com.practiceq.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionResponse {

    private String prescriptionId;
    private List<String> medicines;
    private String duration;



    private Patient patient;
    private Doctor doctor;
}