package com.practiceQ.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.practiceQ.entity.Appointments;
import com.practiceQ.entity.Doctor;
import com.practiceQ.entity.Patient;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsDto {

   private String appId;

   @NotEmpty(message = "Date must not be empty")
   private String date;

   @NotEmpty(message = "Time must not be empty")
   private String time;

   @NotEmpty(message = "Reason must not be empty")
   private String reason;

   private Boolean emergency;

   @NotEmpty(message = "Patient Id must not be empty")
   private String patientId;
   @NotEmpty(message = "Doctor Id must not be empty")
   private String  doctorId;
}
