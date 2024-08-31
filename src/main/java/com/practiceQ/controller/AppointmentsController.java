package com.practiceQ.controller;

import com.practiceQ.entity.Appointments;
import com.practiceQ.payload.AppointmentsDto;
import com.practiceQ.service.AppointmentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentsController {

    @Autowired
    private AppointmentsService  appointmentsService;

    @PostMapping("/createApp")
    public ResponseEntity<?> createAppointments(@Valid @RequestBody AppointmentsDto appointmentsDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult
                    .getFieldError()
                    .getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Appointments appointments = appointmentsService.createAppointments(appointmentsDto);
        return new ResponseEntity<>(appointments, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Appointments> updateAppointments(@RequestBody Appointments appointments){
        Appointments appointments1 = appointmentsService.updateAppointments(appointments);
        return new ResponseEntity<>(appointments1,HttpStatus.OK);
    }

    @GetMapping("/{patientId}/{appId}")
    public ResponseEntity<Appointments> getByPatient(@PathVariable String patientId,@PathVariable String appId){
        Appointments appointments = appointmentsService.getByPatient(patientId, appId);
        return new ResponseEntity<>(appointments,HttpStatus.OK);
    }


}
