package com.practiceQ.service;

import com.practiceQ.entity.AcceptAppointment;
import com.practiceQ.entity.Doctor;
import com.practiceQ.payload.DoctorAppointmentsDto;
import com.practiceQ.payload.DoctorDto;
import com.practiceQ.payload.ListOfDoctorsForPatient;
import com.practiceQ.payload.SignInDto;

public interface DoctorService {

    Doctor signup(DoctorDto doctorDto);
    DoctorAppointmentsDto findById(String doctorId, int pageNo, int pageSize);
    DoctorAppointmentsDto findById(String doctorId,boolean search, int pageNo, int pageSize);

    boolean existsByEmail(String email);

    String verifyLogin(SignInDto signInDto);

    ListOfDoctorsForPatient profile(Doctor doctor);

    AcceptAppointment getByDoctorId(String doctorId, String acceptAppId);
}
