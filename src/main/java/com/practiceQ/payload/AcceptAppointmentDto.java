package com.practiceQ.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptAppointmentDto {

    @NotEmpty(message = "Must not be empty")
    public String appointmentId;
    @NotEmpty(message = "Must not be empty")
    public String doctorId;
}
