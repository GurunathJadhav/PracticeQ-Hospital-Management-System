package com.practiceq.service.Impl;

import com.practiceq.entity.Staff;
import com.practiceq.exception.DepartmentNotFoundException;
import com.practiceq.payload.StaffDto;
import com.practiceq.repository.DepartmentRepository;
import com.practiceq.repository.StaffRepository;
import com.practiceq.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Staff createStaff(StaffDto staffDto) {
        Staff staff=new Staff();
        staff.setStaffId(UUID.randomUUID().toString());
        staff.setFirstName(staffDto.getFirstName());
        staff.setLastName(staffDto.getLastName());
        staff.setPhoneNumber(staffDto.getPhoneNumber());

        staff.setDepartment(departmentRepository.findById(staffDto.getDepartmentId())
                .orElseThrow(()-> new DepartmentNotFoundException("No department")));
        return staffRepository.save(staff);
    }
}
