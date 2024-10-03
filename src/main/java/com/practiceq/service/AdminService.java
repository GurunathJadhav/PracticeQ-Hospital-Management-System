package com.practiceq.service;

import com.practiceq.entity.Admin;
import com.practiceq.payload.SignInDto;

import java.io.IOException;

public interface AdminService {

    Admin signUp(Admin admin);
    boolean existsByRole(String role);

    String verifyLogin(SignInDto signInDto );

    String generateInvoice(String patientId) throws IOException;
}
