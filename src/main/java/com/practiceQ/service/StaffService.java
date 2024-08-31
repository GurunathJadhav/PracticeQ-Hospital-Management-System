package com.practiceQ.service;

import com.practiceQ.entity.Staff;
import com.practiceQ.payload.StaffDto;

public interface StaffService {

    Staff createStaff(StaffDto staffDto);
}
