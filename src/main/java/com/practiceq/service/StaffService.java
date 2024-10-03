package com.practiceq.service;

import com.practiceq.entity.Staff;
import com.practiceq.payload.StaffDto;

public interface StaffService {

    Staff createStaff(StaffDto staffDto);
}
