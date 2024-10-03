package com.practiceq.service;

import com.practiceq.entity.AcceptAppointment;
import com.practiceq.entity.Doctor;
import com.practiceq.payload.DoctorAppointmentsDto;
import com.practiceq.payload.DoctorDto;
import com.practiceq.payload.ListOfDoctorsForPatient;
import com.practiceq.payload.SignInDto;

public interface DoctorService {

    Doctor signup(DoctorDto doctorDto);
    DoctorAppointmentsDto findById(String doctorId, int pageNo, int pageSize);
    DoctorAppointmentsDto findById(String doctorId,boolean search, int pageNo, int pageSize);

    boolean existsByEmail(String email);

    String verifyLogin(SignInDto signInDto);

    ListOfDoctorsForPatient profile(Doctor doctor);

    AcceptAppointment getByDoctorId(String doctorId, String acceptAppId);
}
