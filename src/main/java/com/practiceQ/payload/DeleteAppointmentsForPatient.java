package com.practiceQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAppointmentsForPatient {

    private String patientId;
    private String appointmentId;
}
