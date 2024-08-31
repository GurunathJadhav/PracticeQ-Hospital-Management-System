package com.practiceQ.service;

import com.practiceQ.entity.Department;
import com.practiceQ.payload.DepartmentDto;

public interface DepartmentService {

    Department createDepartment(DepartmentDto departmentDto);
}
