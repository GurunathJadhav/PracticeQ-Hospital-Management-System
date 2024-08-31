package com.practiceQ.config;


import com.practiceQ.entity.Admin;
import com.practiceQ.entity.Doctor;
import com.practiceQ.entity.Patient;
import com.practiceQ.exception.AdminNotFoundException;
import com.practiceQ.exception.DoctorNotFoundException;
import com.practiceQ.repository.AdminRepository;
import com.practiceQ.repository.DoctorRepository;
import com.practiceQ.repository.PatientRepository;
import com.practiceQ.service.Impl.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.ProviderNotFoundException;
import java.util.Collections;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");

        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){

            String token = tokenHeader.substring(7);

            String role = jwtService.getRole(token);
           if(role.equals("ROLE_USER")){
               Patient patient = patientRepository.findByFirstName(jwtService.getUsername(token)).orElseThrow(() ->
                       new ProviderNotFoundException("Patient not found"));

               UsernamePasswordAuthenticationToken authentication=
                       new UsernamePasswordAuthenticationToken(patient,null, Collections.singleton(
                               new SimpleGrantedAuthority(patient.getRole())
                       ));

               authentication.setDetails(new WebAuthenticationDetails(request));

               SecurityContextHolder.getContext().setAuthentication(authentication);
           } else if (role.equals("ROLE_ADMIN")) {
               Admin admin = adminRepository.findByFirstName(jwtService.getUsername(token)).orElseThrow(() ->
                       new AdminNotFoundException("Admin not found"));

               UsernamePasswordAuthenticationToken authentication=
                       new UsernamePasswordAuthenticationToken(admin,null, Collections.singleton(
                               new SimpleGrantedAuthority(admin.getRole())
                       ));

               authentication.setDetails(new WebAuthenticationDetails(request));

               SecurityContextHolder.getContext().setAuthentication(authentication);



           } else if (role.equals("ROLE_DOCTOR")) {

               Doctor doctor = doctorRepository.findByFirstName(jwtService.getUsername(token)).orElseThrow(() ->
                       new DoctorNotFoundException("Doctor not found"));

               UsernamePasswordAuthenticationToken authentication=
                       new UsernamePasswordAuthenticationToken(doctor,null, Collections.singleton(
                               new SimpleGrantedAuthority(doctor.getRole())
                       ));

               authentication.setDetails(new WebAuthenticationDetails(request));

               SecurityContextHolder.getContext().setAuthentication(authentication);

           }


        }
        filterChain.doFilter(request,response);
    }
}