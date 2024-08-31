package com.practiceQ.controller;

import com.practiceQ.entity.Staff;
import com.practiceQ.payload.StaffDto;
import com.practiceQ.service.StaffService;
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
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping
    public ResponseEntity<?> createStaff(@Valid  @RequestBody StaffDto staffDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult
                    .getFieldError()
                    .getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Staff staff = staffService.createStaff(staffDto);

        return new ResponseEntity<>(staff, HttpStatus.CREATED);
    }
}
