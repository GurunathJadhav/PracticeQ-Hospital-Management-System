package com.practiceQ.payload;

import com.practiceQ.entity.Doctor;
import com.practiceQ.entity.Patient;
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