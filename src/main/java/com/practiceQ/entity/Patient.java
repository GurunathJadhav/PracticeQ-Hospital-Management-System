package com.practiceQ.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "patient")
public class Patient {
    @Id

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @NotEmpty
    @Size(min=3,message = "First Name should be atleast 3 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty
    @Size(min=3,message = "Last Name should be atleast 3 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty(message = "Email must not be empty")
    @Column(name = "email", nullable = false, length = 500,unique = true)
    private String email;

    @NotEmpty
    @Size(min=5 , message = "Password must atleast 5 characters")
    @Column(name = "password", nullable = false)
    private String password;


    @Column(name = "role", nullable = false, length = 100)
    private String role;

    //    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Column(name = "mobile", nullable = false)
    private Long mobile;



}