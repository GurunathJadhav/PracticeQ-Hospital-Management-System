package com.practiceQ.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private String doctorId;

    @NotEmpty(message = "Must not be empty")
    @Size(min =3,message = "First Name must be at least 3 characters")
    private String firstName;

    @NotEmpty(message = "Must not be empty")
    @Size(min =3,message = "Last Name must be at least 3 characters")
    private String lastName;

    @NotEmpty(message = "Must not be empty")
    private String email;

    @NotEmpty(message = "Must not be empty")
    @Size(min = 5 ,message = "Password must be at least 3 characters")
    private String password;

    private String role;
    @NotEmpty(message = "Must not be empty")
    private String specialization;
    @NotEmpty(message = "Must not be empty")
    private String departmentId;


}
