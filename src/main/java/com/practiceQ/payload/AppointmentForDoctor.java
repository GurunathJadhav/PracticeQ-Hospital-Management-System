package com.practiceQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentForDoctor {


    private String appId;

    private String date;

    private String time;

    private String reason;

    private Boolean emergency ;

    private PatientDto patientDto;

}
