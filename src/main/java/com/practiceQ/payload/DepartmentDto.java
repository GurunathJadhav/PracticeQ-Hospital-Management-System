package com.practiceQ.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    @NotEmpty(message = "Department Name must not be empty")
    @Size(min = 3,message = "Department Name must be at least 3 characters")
    private String departmentName;
    @NotEmpty(message = "Hospital Id must not be empty")
    private String hospitalId;
}
