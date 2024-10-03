package com.practiceq.service.Impl;

import com.practiceq.entity.*;
import com.practiceq.exception.DepartmentNotFoundException;
import com.practiceq.exception.DoctorNotFoundException;
import com.practiceq.exception.HospitalNotFoundException;
import com.practiceq.exception.PatientNotFoundException;
import com.practiceq.payload.*;
import com.practiceq.repository.*;
import com.practiceq.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentsRepository appointmentsRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Override
    public Patient signup(Patient patient) {
        patient.setPatientId(UUID.randomUUID().toString());
        patient.setPassword(BCrypt.hashpw(patient.getPassword(),BCrypt.gensalt(10)));
        return patientRepository.save(patient);
    }

    @Override
    public String verifyLogin(SignInDto signinDto) {

        Patient patient = patientRepository.findByFirstName(signinDto.getUsername()).orElseThrow(() ->
                new PatientNotFoundException("Patient not found"));
        if(BCrypt.checkpw(signinDto.getPassword(),patient.getPassword())){
            return jwtService.generateToken(patient);
        }

        return null;
    }


    @Override
    public PatientAppointmentsDto findById(String patientId, String search) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()-> new PatientNotFoundException("Patient not found for id "+patientId));
        PatientAppointmentsDto patientAppointmentsDto = new PatientAppointmentsDto();

        List<Appointments> allByPatient = appointmentsRepository.findAllByPatientBySearch(patient,search);

        List<ListOfAppointmentsOfPatient> collect = allByPatient.stream().map(appointment -> {
            ListOfAppointmentsOfPatient listOfAppointmentsOfPatient = new ListOfAppointmentsOfPatient();
            listOfAppointmentsOfPatient.setAppId(appointment.getAppId());
            listOfAppointmentsOfPatient.setDate(appointment.getDate());
            listOfAppointmentsOfPatient.setTime(appointment.getTime());
            listOfAppointmentsOfPatient.setReason(appointment.getReason());
            listOfAppointmentsOfPatient.setEmergency(appointment.getEmergency());
            Doctor doctor = doctorRepository.findById(appointment.getDoctor().getDoctorId())
                    .orElseThrow(()-> new DoctorNotFoundException("Doctor not found"));
            ListOfDoctorsForPatient doctorDto = new ListOfDoctorsForPatient();

            doctorDto.setFirstName(doctor.getFirstName());
            doctorDto.setDoctorId(doctor.getDoctorId());
            doctorDto.setLastName(doctor.getLastName());
            doctorDto.setEmail(doctor.getEmail());
            doctorDto.setSpecialization(doctor.getSpecialization());
            Department department = departmentRepository.findById(doctor.getDepartment().getDepartmentId())
                    .orElseThrow(()-> new DepartmentNotFoundException("Department Not found"));
            DepartmentInfo departmentInfo = new DepartmentInfo();
            departmentInfo.setDepartmentName(department.getDepartmentName());
            Hospital hospital = hospitalRepository.findById(department.getHospital().getHospitalId())
                    .orElseThrow(()-> new HospitalNotFoundException("Hospital Not Found"));

            HospitalInfo hospitalInfo = new HospitalInfo();
            hospitalInfo.setHospitalName(hospital.getHospitalName());
            hospitalInfo.setPhoneNumber(hospital.getPhoneNumber());
            hospitalInfo.setState(hospital.getState());
            hospitalInfo.setZipCode(hospital.getZipCode());

            departmentInfo.setHospitalInfo(hospitalInfo);

            doctorDto.setDepartmentInfo(departmentInfo);

            listOfAppointmentsOfPatient.setDoctor(doctorDto);
            return listOfAppointmentsOfPatient;

        }).collect(Collectors.toList());

        patientAppointmentsDto.setFirstName(patient.getFirstName());
        patientAppointmentsDto.setLastName(patient.getLastName());
        patientAppointmentsDto.setEmail(patient.getEmail());
        patientAppointmentsDto.setPatientAppointmentsList(collect);

        return patientAppointmentsDto;
    }

    @Override
    public List<ListOfDoctorsForPatient> getAllDoctors(int pageNo, int pageSize) {

        Pageable page = PageRequest.of(pageNo, pageSize);
        Page<Doctor> all = doctorRepository.findAll(page);

        List<Doctor> allDoctorsData = all.getContent();

        List<ListOfDoctorsForPatient> allDoctors = allDoctorsData.stream().map(doctor -> {
            ListOfDoctorsForPatient listOfDoctorsForPatient = new ListOfDoctorsForPatient();
            listOfDoctorsForPatient.setFirstName(doctor.getFirstName());
            listOfDoctorsForPatient.setDoctorId(doctor.getDoctorId());
            listOfDoctorsForPatient.setLastName(doctor.getLastName());
            listOfDoctorsForPatient.setEmail(doctor.getEmail());
            listOfDoctorsForPatient.setSpecialization(doctor.getSpecialization());
            Department department = departmentRepository.findById(doctor.getDepartment().getDepartmentId())
                    .orElseThrow(()-> new DepartmentNotFoundException("Department Not found"));
            DepartmentInfo departmentInfo = new DepartmentInfo();
            departmentInfo.setDepartmentName(department.getDepartmentName());
            Hospital hospital = hospitalRepository.findById(department.getHospital().getHospitalId())
                    .orElseThrow(()-> new HospitalNotFoundException("Hospital Not Found"));

            HospitalInfo hospitalInfo = new HospitalInfo();
            hospitalInfo.setHospitalName(hospital.getHospitalName());
            hospitalInfo.setPhoneNumber(hospital.getPhoneNumber());
            hospitalInfo.setState(hospital.getState());
            hospitalInfo.setZipCode(hospital.getZipCode());

            departmentInfo.setHospitalInfo(hospitalInfo);

            listOfDoctorsForPatient.setDepartmentInfo(departmentInfo);

            return listOfDoctorsForPatient;

        }).collect(Collectors.toList());


        return allDoctors;
    }

    @Override
    public String deleteAppointment(String patientId,String appId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()-> new PatientNotFoundException("Patient not found for id "+patientId));

        List<Appointments> allAppointments = appointmentsRepository.findAllByPatient(patient);

        Appointments appointments = allAppointments.stream().filter(appointment -> appointment.getAppId().equals(appId)).findFirst().orElse(null);

        if (appointments!=null){
            appointmentsRepository.delete(appointments);
            return "Appointment deleted successfully";
        }else {
            return "Appointment Not Found";
        }
    }

    @Override
    public List<ListOfDoctorsForPatient> searchDoctor(String search, int pageNo, int pageSize) {

        Pageable pageable=PageRequest.of(pageNo,pageSize);

        Page<Doctor> all = doctorRepository.searchDoctor(search, pageable);
        List<Doctor> doctors = all.getContent();


        List<ListOfDoctorsForPatient> listOfDoctors = doctors.stream().map(doctor -> {

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

        }).collect(Collectors.toList());


        return listOfDoctors;
    }

    @Override
    public boolean existsByEmail(String email) {

        return  patientRepository.existsByEmail(email);
    }

    @Override
    public PatientWithInvoice getAllInvoice(String patientId) {
        PatientWithInvoice patientWithInvoice = new PatientWithInvoice();

        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new PatientNotFoundException("Patient not found"));
        patientWithInvoice.setFirstName(patient.getFirstName());
        patientWithInvoice.setLastName(patient.getLastName());
        patientWithInvoice.setEmail(patient.getEmail());
        patientWithInvoice.setMobile(patient.getMobile());

        List<Invoice> allInvoices = invoiceRepository.findAllByPatient(patient);

        List<InvoiceWithService> listOfInvoices = allInvoices.stream().map(invoice -> {
            InvoiceWithService invoiceWithService = new InvoiceWithService();
            invoiceWithService.setDate(invoice.getDate());
            invoiceWithService.setTotalAmount(invoice.getTotalAmount());

            List<Services> allServices = servicesRepository.findAllByInvoice(invoice);

            List<ServiceListForInvoice> services = allServices.stream().map(service -> {
                ServiceListForInvoice serviceListForInvoice = new ServiceListForInvoice();
                serviceListForInvoice.setServiceId(service.getServiceId());
                serviceListForInvoice.setServiceName(service.getServiceName());
                serviceListForInvoice.setCost(service.getCost());

                return serviceListForInvoice;
            }).collect(Collectors.toList());

            invoiceWithService.setServiceListForInvoiceList(services);
            return invoiceWithService;

        }).collect(Collectors.toList());

        patientWithInvoice.setInvoiceWithServiceList(listOfInvoices);

        return patientWithInvoice;
    }


    @Override
    public ListOfDoctorsForPatient getDoctorById(String doctorId){
        ListOfDoctorsForPatient listOfDoctorsForPatient=new ListOfDoctorsForPatient();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() ->
                new DoctorNotFoundException("Doctor not found"));

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
    public PatientAppointmentsDto findById(String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()-> new PatientNotFoundException("Patient not found for id "+patientId));
        PatientAppointmentsDto patientAppointmentsDto = new PatientAppointmentsDto();

        List<Appointments> allByPatient = appointmentsRepository.findAllByPatient(patient);
        List<ListOfAppointmentsOfPatient> collect = allByPatient.stream().map(appointment -> {
            ListOfAppointmentsOfPatient listOfAppointmentsOfPatient = new ListOfAppointmentsOfPatient();
            listOfAppointmentsOfPatient.setAppId(appointment.getAppId());
            listOfAppointmentsOfPatient.setDate(appointment.getDate());
            listOfAppointmentsOfPatient.setTime(appointment.getTime());
            listOfAppointmentsOfPatient.setReason(appointment.getReason());
            listOfAppointmentsOfPatient.setEmergency(appointment.getEmergency());
            Doctor doctor = doctorRepository.findById(appointment.getDoctor().getDoctorId())
                    .orElseThrow(()-> new DoctorNotFoundException("Doctor not found"));
            ListOfDoctorsForPatient doctorDto = new ListOfDoctorsForPatient();

            doctorDto.setFirstName(doctor.getFirstName());
            doctorDto.setDoctorId(doctor.getDoctorId());
            doctorDto.setLastName(doctor.getLastName());
            doctorDto.setEmail(doctor.getEmail());
            doctorDto.setSpecialization(doctor.getSpecialization());
            Department department = departmentRepository.findById(doctor.getDepartment().getDepartmentId())
                    .orElseThrow(()-> new DepartmentNotFoundException("Department Not found"));
            DepartmentInfo departmentInfo = new DepartmentInfo();
            departmentInfo.setDepartmentName(department.getDepartmentName());
            Hospital hospital = hospitalRepository.findById(department.getHospital().getHospitalId())
                    .orElseThrow(()-> new HospitalNotFoundException("Hospital Not Found"));

            HospitalInfo hospitalInfo = new HospitalInfo();
            hospitalInfo.setHospitalName(hospital.getHospitalName());
            hospitalInfo.setPhoneNumber(hospital.getPhoneNumber());
            hospitalInfo.setState(hospital.getState());
            hospitalInfo.setZipCode(hospital.getZipCode());

            departmentInfo.setHospitalInfo(hospitalInfo);

            doctorDto.setDepartmentInfo(departmentInfo);

            listOfAppointmentsOfPatient.setDoctor(doctorDto);
            return listOfAppointmentsOfPatient;

        }).collect(Collectors.toList());

        patientAppointmentsDto.setFirstName(patient.getFirstName());
        patientAppointmentsDto.setLastName(patient.getLastName());
        patientAppointmentsDto.setEmail(patient.getEmail());
        patientAppointmentsDto.setPatientAppointmentsList(collect);

        return patientAppointmentsDto;
    }


}
