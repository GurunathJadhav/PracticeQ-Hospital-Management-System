package com.practiceQ.service;

import com.practiceQ.entity.AcceptAppointment;
import com.practiceQ.payload.AcceptAppointmentDto;

public interface AcceptAppointmentService {

    AcceptAppointment acceptAppointment(AcceptAppointmentDto acceptAppointmentDto);
}
