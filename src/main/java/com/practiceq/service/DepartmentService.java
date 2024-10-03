package com.practiceq.service;

import com.practiceq.entity.Department;
import com.practiceq.payload.DepartmentDto;

public interface DepartmentService {

    Department createDepartment(DepartmentDto departmentDto);
}
