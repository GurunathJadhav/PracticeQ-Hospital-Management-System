package com.practiceq.service;

import com.practiceq.entity.Patient;
import com.practiceq.payload.*;

import java.util.List;

public interface PatientService {

    Patient signup(Patient patient);

    PatientAppointmentsDto findById(String patientId, String search);

    List<ListOfDoctorsForPatient> getAllDoctors(int pageNo, int pageSize);

    String deleteAppointment(String patientId,String appId);

    List<ListOfDoctorsForPatient> searchDoctor(String search, int pageNo, int pageSize);

    boolean existsByEmail(String email);

    PatientWithInvoice getAllInvoice(String patientId);

    String verifyLogin(SignInDto signinDto);
    ListOfDoctorsForPatient getDoctorById(String doctorId);

    PatientAppointmentsDto findById(String patientId);


}
