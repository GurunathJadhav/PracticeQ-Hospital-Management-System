package com.practiceQ.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomsDto {

    private String admissionDate;

    private Integer totalBeds;

    @NotEmpty(message = "Staff Id must not be empty")
    private String staffId;
    @NotEmpty(message = "Patient Id must not be empty")
    private String patientId;


}
