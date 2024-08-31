package com.practiceQ.service.Impl;

import com.practiceQ.entity.*;

import com.practiceQ.exception.*;
import com.practiceQ.payload.*;
import com.practiceQ.repository.*;

import com.practiceQ.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JWTService  jwtService;

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private AcceptAppointmentRepository acceptAppointmentRepository;

    @Override
    public Doctor signup(DoctorDto doctorDto) {

        Doctor doctor = new Doctor();
        doctor.setDoctorId(UUID.randomUUID().toString());
        doctor.setFirstName(doctorDto.getFirstName());
        doctor.setLastName(doctorDto.getLastName());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setPassword(BCrypt.hashpw(doctorDto.getPassword(),BCrypt.gensalt(10)));
        doctor.setRole(doctorDto.getRole());

        doctor.setDepartment(departmentRepository.findById(doctorDto.getDepartmentId()).orElseThrow(()
                -> new DepartmentNotFoundException("Department is not found for this department id  " + doctorDto.getDepartmentId())));

        return doctorRepository.save(doctor);

    }

    @Override
    public String verifyLogin(SignInDto signInDto) {

        Doctor doctor = doctorRepository.findByFirstName(signInDto.getUsername()).orElseThrow(() ->
                new DoctorNotFoundException("Doctor not found "));
        if(BCrypt.checkpw(signInDto.getPassword(),doctor.getPassword())){
            return jwtService.generateTokenForDoctor(doctor);
        }
        return null;
    }

    @Override
    public ListOfDoctorsForPatient profile(Doctor doctor) {

        ListOfDoctorsForPatient listOfDoctorsForPatient = new ListOfDoctorsForPatient();
        listOfDoctorsForPatient.setFirstName(doctor.getFirstName());
        listOfDoctorsForPatient.setDoctorId(doctor.getDoctorId());
        listOfDoctorsForPatient.setLastName(doctor.getLastName());
        listOfDoctorsForPatient.setEmail(doctor.getEmail());
        listOfDoctorsForPatient.setSpecialization(doctor.getSpecialization());

        Department department = departmentRepository.findById(doctor.getDepartment().getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department Not found"));
        DepartmentInfo departmentInfo = new DepartmentInfo();
        departmentInfo.setDepartmentName(department.getDepartmentName());

        Hospital hospital = hospitalRepository.findById(department.getHospital().getHospitalId())
                .orElseThrow(() -> new HospitalNotFoundException("Hospital Not Found"));

        HospitalInfo hospitalInfo = new HospitalInfo();
        hospitalInfo.setHospitalName(hospital.getHospitalName());
        hospitalInfo.setPhoneNumber(hospital.getPhoneNumber());
        hospitalInfo.setState(hospital.getState());
        hospitalInfo.setZipCode(hospital.getZipCode());

        departmentInfo.setHospitalInfo(hospitalInfo);

        listOfDoctorsForPatient.setDepartmentInfo(departmentInfo);
        return listOfDoctorsForPatient;
    }

    @Override
    public AcceptAppointment getByDoctorId(String doctorId, String acceptAppId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() ->
                new DoctorNotFoundException("Doctor not found"));

        AcceptAppointment acceptApp = new AcceptAppointment();
        List<AcceptAppointment> list = acceptAppointmentRepository.findAllByDoctor(doctor);

       list.stream().map(appointment->{
           if(appointment.getAcapId().equals(acceptAppId)){
               acceptApp.setAcapId(appointment.getAcapId());
               acceptApp.setDoctor(doctorRepository.findById(appointment.getDoctor().getDoctorId()).orElseThrow(()-> new DoctorNotFoundException("Doctor not found")));
               acceptApp.setAppointments(appointmentsRepository.findById(appointment.getAppointments().getAppId()).orElseThrow(()-> new AppointmentNotFoundException("Appointments not found")));
               return  acceptApp;
           }else {
               return null;
           }
       }).collect(Collectors.toList());
        return acceptApp;
    }


    @Override
    public DoctorAppointmentsDto findById(String doctorId, int pageNo, int pageSize) {
        DoctorAppointmentsDto doctorAppointmentsDto = new DoctorAppointmentsDto();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(()-> new DoctorNotFoundException("Doctor is not found for id "+doctorId));

        Pageable pageable= PageRequest.of(pageNo, pageSize);
        Page<Appointments> all = appointmentsRepository.findAllByDoctor(doctor, pageable);
        List<Appointments> allAppointments = all.getContent();
        List<AppointmentForDoctor> collect = allAppointments.stream().map(appointments -> {
            AppointmentForDoctor appointmentForDoctor = new AppointmentForDoctor();
            appointmentForDoctor.setAppId(appointments.getAppId());
            appointmentForDoctor.setDate(appointments.getDate());
            appointmentForDoctor.setTime(appointments.getTime());
            appointmentForDoctor.setReason(appointments.getReason());
            appointmentForDoctor.setEmergency(appointments.getEmergency());

            PatientDto patientDto = new PatientDto();
            Patient patient = patientRepository.findById(appointments.getPatient().getPatientId()).orElseThrow(()->
                    new PatientNotFoundException("Patient is not found for id "+appointments.getPatient().getPatientId())
            );
            patientDto.setPatientId(patient.getPatientId());
            patientDto.setFirstName(patient.getFirstName());
            patientDto.setLastName(patient.getLastName());
            patientDto.setMobile(patient.getMobile());
            patientDto.setEmail(patient.getEmail());

            appointmentForDoctor.setPatientDto(patientDto);
            return appointmentForDoctor;

        }).collect(Collectors.toList());

        doctorAppointmentsDto.setFirstName(doctor.getFirstName());
        doctorAppointmentsDto.setLastName(doctor.getLastName());
        doctorAppointmentsDto.setSpecialization(doctor.getSpecialization());
        doctorAppointmentsDto.setAppointmentForDoctor(collect);


        return doctorAppointmentsDto;
    }

    @Override
    public DoctorAppointmentsDto findById(String doctorId, boolean search,int pageNo,int pageSize) {
        DoctorAppointmentsDto doctorAppointmentsDto = new DoctorAppointmentsDto();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(()-> new DoctorNotFoundException("Doctor is not found for id "+doctorId));

        Pageable pageable= PageRequest.of(pageNo, pageSize);

        Page<Appointments> allByDoctorWithSearch = appointmentsRepository.findAllByDoctorWithSearch(doctor, search, pageable);

        List<Appointments> allAppointments = allByDoctorWithSearch.getContent();

        List<AppointmentForDoctor> collect = allAppointments.stream().map(appointments -> {
            AppointmentForDoctor appointmentForDoctor = new AppointmentForDoctor();
            appointmentForDoctor.setAppId(appointments.getAppId());
            appointmentForDoctor.setDate(appointments.getDate());
            appointmentForDoctor.setTime(appointments.getTime());
            appointmentForDoctor.setReason(appointments.getReason());
            appointmentForDoctor.setEmergency(appointments.getEmergency());

            PatientDto patientDto = new PatientDto();
            Patient patient = patientRepository.findById(appointments.getPatient().getPatientId()).orElseThrow(()->
                    new PatientNotFoundException("Patient is not found for id "+appointments.getPatient().getPatientId())
            );
            patientDto.setPatientId(patient.getPatientId());
            patientDto.setFirstName(patient.getFirstName());
            patientDto.setLastName(patient.getLastName());
            patientDto.setMobile(patient.getMobile());
            patientDto.setEmail(patient.getEmail());

            appointmentForDoctor.setPatientDto(patientDto);
            return appointmentForDoctor;

        }).collect(Collectors.toList());

        doctorAppointmentsDto.setFirstName(doctor.getFirstName());
        doctorAppointmentsDto.setLastName(doctor.getLastName());
        doctorAppointmentsDto.setSpecialization(doctor.getSpecialization());
        doctorAppointmentsDto.setAppointmentForDoctor(collect);


        return doctorAppointmentsDto;


    }

    @Override
    public boolean existsByEmail(String email) {

        return doctorRepository.existsByEmail(email);
    }


}
