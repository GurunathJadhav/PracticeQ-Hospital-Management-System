package com.practiceq.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentInfo {

    private String departmentName;
    private HospitalInfo hospitalInfo;
}
