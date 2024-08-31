package com.practiceQ.controller;

import com.practiceQ.entity.Prescription;
import com.practiceQ.payload.PrescriptionDto;
import com.practiceQ.payload.PrescriptionResponse;
import com.practiceQ.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<?> createPrescription(@Valid @RequestBody PrescriptionDto prescriptionDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult
                    .getFieldError()
                    .getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PrescriptionResponse prescription = prescriptionService.createPrescription(prescriptionDto);
        return new ResponseEntity<>(prescription, HttpStatus.CREATED);
    }
}
