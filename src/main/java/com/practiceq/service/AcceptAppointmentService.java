package com.practiceq.service;

import com.practiceq.entity.AcceptAppointment;
import com.practiceq.payload.AcceptAppointmentDto;

public interface AcceptAppointmentService {

    AcceptAppointment acceptAppointment(AcceptAppointmentDto acceptAppointmentDto);
}
