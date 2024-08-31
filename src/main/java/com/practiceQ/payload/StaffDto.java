package com.practiceQ.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDto {

    private String staffId;

    @NotEmpty(message = "First Name must not be empty")
    @Size(min = 3,message = "First Name must be at least 3 characters")
    private String firstName;

    @NotEmpty(message = "Last Name must not be empty")
    @Size(min = 3,message = "Last Name must be at least 3 characters")
    private String lastName;

    //    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private Long phoneNumber;

    @NotEmpty(message = "Department Id must not be empty")
    private String departmentId;
}
