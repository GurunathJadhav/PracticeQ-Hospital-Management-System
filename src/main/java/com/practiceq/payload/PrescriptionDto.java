package com.practiceq.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto {


    @NotEmpty(message = "Prescription Name must not be empty")
    private List<String> medicines;

    @NotEmpty(message = "Prescription Validity  must not be empty")
    private String duration;



    @NotEmpty(message = "Patient Id must not be empty")
    private String patientId;

    @NotEmpty(message = "Doctor Id must not be empty")
    private String doctorId;
}
