package com.practiceQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientAppointmentsDto {

    private String firstName;
    private String lastName;
    private String email;

    List<ListOfAppointmentsOfPatient> patientAppointmentsList;
}
