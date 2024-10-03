package com.practiceq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@Configuration
public class SecurityConfig {

   @Autowired
    private JWTRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().cors().configurationSource(corsConfigurationSource());
      http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
     http.authorizeHttpRequests()
             .requestMatchers("/api/patient/signup",
                     "/api/patient/sign-in",
                     "/api/admin/signup",
                     "/api/admin/sign-in",
                     "/api/doctor/sign-in").permitAll()
             .requestMatchers("/api/patient/**",
                     "/api/appointments/**")
             .hasRole("USER")
             .requestMatchers(
                     "/api/admin/**",
                     "/api/department/**",
                     "/api/hospitals/**",
                     "/api/services/**",
                     "/api/invoices/**",
                     "/api/staff/**",
                     "/api/rooms/**"
                     ).hasRole("ADMIN")
             .requestMatchers("/api/doctor/**",
                     "/api/prescriptions/**").hasRole("DOCTOR")
             .anyRequest()
             .authenticated();

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); // Allow Angular frontend origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true); // Allow credentials like cookies and Authorization headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
