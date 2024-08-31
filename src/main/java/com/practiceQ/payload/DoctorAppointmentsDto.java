package com.practiceQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAppointmentsDto {

    private String firstName;
    private String lastName;
    private String specialization;

    List<AppointmentForDoctor> appointmentForDoctor;

}
