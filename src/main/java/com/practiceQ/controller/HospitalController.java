package com.practiceQ.controller;

import com.practiceQ.entity.Hospital;
import com.practiceQ.service.HospitalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping
    public ResponseEntity<?> createHospital(@Valid @RequestBody Hospital hospital, BindingResult bindingResult){
       if(bindingResult.hasErrors()){
           return new ResponseEntity<>(bindingResult
                   .getFieldError()
                   .getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
       }
        Hospital hospital1 = hospitalService.createHospital(hospital);
        return new ResponseEntity<>(hospital1, HttpStatus.CREATED);
    }
}
