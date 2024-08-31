package com.practiceQ.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private String patientId;

    private String firstName;

    private String lastName;

    private String email;

    @JsonIgnore
    private String password;


    @JsonIgnore
    private String role;

    private Long mobile;
}