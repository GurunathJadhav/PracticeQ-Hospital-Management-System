package com.practiceQ.service.Impl;


import com.practiceQ.entity.Department;
import com.practiceQ.entity.Hospital;
import com.practiceQ.exception.HospitalNotFoundException;
import com.practiceQ.payload.DepartmentDto;
import com.practiceQ.repository.DepartmentRepository;
import com.practiceQ.repository.HospitalRepository;
import com.practiceQ.service.DepartmentService;
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
