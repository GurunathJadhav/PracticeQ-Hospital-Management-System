package com.practiceq.controller;


import com.practiceq.appcontants.Constants;
import com.practiceq.entity.Patient;
import com.practiceq.payload.*;

import com.practiceq.service.Impl.EmailService;
import com.practiceq.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String,Object>> signup(@Valid @RequestBody Patient patient, BindingResult bindingResult){
       Map<String,Object> response =new HashMap<>();
        if(bindingResult.hasErrors()){
            response.put("validationError",bindingResult
                    .getFieldError()
                    .getDefaultMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(patientService.existsByEmail(patient.getEmail())){
            response.put("emailExists", "Email already exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Patient signup = patientService.signup(patient);

        emailService.sendEmail(patient.getEmail(),"PracticeQ Registration","You are successfully registered as patient in PracticeQ.");
        response.put("patient",signup);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Map<String,String>> signIn(@RequestBody SignInDto signinDto){
        String token = patientService.verifyLogin(signinDto);

        Map<String,String> response=new HashMap<>();

        if(token!=null){
            response.put("token",token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("message","Invalid Credentials ");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/profile")
    public ResponseEntity<Patient> getCurrentUser(@AuthenticationPrincipal Patient patient){
        return new ResponseEntity<>(patient,HttpStatus.OK);
    }

    @GetMapping("/{patientId}/{search}")
    public ResponseEntity<PatientAppointmentsDto> allAppointments(@PathVariable String patientId, @PathVariable String search){
        PatientAppointmentsDto byId = patientService.findById(patientId,search);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping("/appointments/{patientId}")
    public ResponseEntity<PatientAppointmentsDto> getALlAppointments(@PathVariable String patientId){
        PatientAppointmentsDto allAppointments = patientService.findById(patientId);
        return new ResponseEntity<>(allAppointments, HttpStatus.OK);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<ListOfDoctorsForPatient>> getAllDoctors(
            @RequestParam(name = "pageNo",defaultValue = Constants.DEFAULT_PAGE_Number) int pageNo,
            @RequestParam(name = "pageSize",defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize
    ){
        List<ListOfDoctorsForPatient> allDoctors = patientService.getAllDoctors(pageNo, pageSize);
        return new ResponseEntity<>(allDoctors, HttpStatus.OK);
    }


    @DeleteMapping("/{patientId}/{appId}")
    public ResponseEntity<Map<String,String>> deleteAppointment(@PathVariable String patientId, @PathVariable String appId){
        String s = patientService.deleteAppointment(patientId, appId);
        Map<String,String> response=new HashMap<>();
        response.put("status",s);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/doctors/{search}")
    public ResponseEntity<List<ListOfDoctorsForPatient>> searchDoctor(@PathVariable String search,
           @RequestParam(name = "pageNo",defaultValue = Constants.DEFAULT_PAGE_Number,required = false) int pageNo,
            @RequestParam(name="pageSize",defaultValue = Constants.DEFAULT_PAGE_SIZE,required = false) int pageSize){
        List<ListOfDoctorsForPatient> listOfDoctorsForPatients = patientService.searchDoctor(search,pageNo,pageSize);
        return new ResponseEntity<>(listOfDoctorsForPatients,HttpStatus.OK);
    }

    @GetMapping("/invoices/{patientId}")
    public ResponseEntity<PatientWithInvoice> getAllInvoices(@PathVariable String patientId){
        PatientWithInvoice allInvoice = patientService.getAllInvoice(patientId);
        return new ResponseEntity<>(allInvoice, HttpStatus.OK);
    }


    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ListOfDoctorsForPatient> getByDoctorById(@PathVariable String doctorId){
        ListOfDoctorsForPatient doctor = patientService.getDoctorById(doctorId);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }


}
