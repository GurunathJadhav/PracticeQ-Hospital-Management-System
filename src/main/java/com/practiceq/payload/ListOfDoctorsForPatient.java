package com.practiceq.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOfDoctorsForPatient {
    private String doctorId;
    private String firstName;

    private String lastName;

    private String email;

    private String specialization;

    private DepartmentInfo departmentInfo;


}
