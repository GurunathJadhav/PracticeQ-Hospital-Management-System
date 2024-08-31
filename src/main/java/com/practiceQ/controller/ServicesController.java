package com.practiceQ.controller;

import com.practiceQ.entity.Services;
import com.practiceQ.payload.ServiceDto;
import com.practiceQ.payload.ServiceWithPatient;
import com.practiceQ.service.ServicesService;
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
@RequestMapping("/api/services")
public class ServicesController {

    @Autowired
    private ServicesService servicesService;

    @PostMapping
    public ResponseEntity<?> createServices(@Valid @RequestBody ServiceDto  serviceDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult
                    .getFieldError()
                    .getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ServiceWithPatient services = servicesService.createServices(serviceDto);
        return new ResponseEntity<>(services, HttpStatus.CREATED);
    }
}
