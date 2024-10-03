package com.practiceq.service.impl;


import com.practiceq.entity.Department;
import com.practiceq.entity.Hospital;
import com.practiceq.exception.HospitalNotFoundException;
import com.practiceq.payload.DepartmentDto;
import com.practiceq.repository.DepartmentRepository;
import com.practiceq.repository.HospitalRepository;
import com.practiceq.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private HospitalRepository hospitalRepository;
    @Override
    public Department createDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setDepartmentId(UUID.randomUUID().toString());
        department.setDepartmentName(departmentDto.getDepartmentName());

        Hospital hospital = hospitalRepository.findById(departmentDto.getHospitalId()).orElseThrow(() -> new HospitalNotFoundException("Hospital is not found for hospital id " + departmentDto.getHospitalId()));

        department.setHospital(hospital);

        return departmentRepository.save(department);
    }
}
