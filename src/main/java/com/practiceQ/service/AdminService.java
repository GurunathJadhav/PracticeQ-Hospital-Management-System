package com.practiceQ.service;

import com.practiceQ.entity.Admin;
import com.practiceQ.payload.SignInDto;

import java.io.IOException;

public interface AdminService {

    Admin signUp(Admin admin);
    boolean existsByRole(String role);

    String verifyLogin(SignInDto signInDto );

    String generateInvoice(String patientId) throws IOException;
}
