package com.practiceQ.controller;

import com.practiceQ.entity.Admin;
import com.practiceQ.entity.Doctor;
import com.practiceQ.payload.DoctorDto;
import com.practiceQ.payload.SignInDto;
import com.practiceQ.service.AdminService;
import com.practiceQ.service.DoctorService;
import com.practiceQ.service.Impl.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private EmailService  emailService;
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid  @RequestBody Admin admin, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult
                    .getFieldError()
                    .getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(adminService.existsByRole(admin.getRole())){
            return new ResponseEntity<>("Admin with this role already exists", HttpStatus.CONFLICT);
        }
        Admin admin1 = adminService.signUp(admin);
        return new ResponseEntity<>(admin1, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInDto signInDto){
        String token = adminService.verifyLogin(signInDto);
        if(token!=null){
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Credential", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/profile")
    public Admin currentAdmin(@AuthenticationPrincipal Admin admin){
        return admin;
    }

    @PostMapping("/addDoctor")
    public ResponseEntity<?> signup(@Valid @RequestBody DoctorDto doctorDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(doctorService.existsByEmail(doctorDto.getEmail())){
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        Doctor signup = doctorService.signup(doctorDto);

        emailService.sendEmail(signup.getEmail(),"PracticeQ Registration","You are successfully registered as a doctor in PracticeQ." +
                "\n Please use this password "+doctorDto.getPassword()+" for login and username will be your first name . Thank You!!");
        return new ResponseEntity<>(signup, HttpStatus.CREATED);
    }

    @PostMapping("/generate-invoice/{patientId}")
    public ResponseEntity<?> generateInvoice(@PathVariable String patientId) throws IOException {

        String s = adminService.generateInvoice(patientId);
        return new ResponseEntity<>(s, HttpStatus.OK);

    }


}
