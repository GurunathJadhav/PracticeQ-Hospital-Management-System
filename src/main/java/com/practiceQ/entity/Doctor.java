package com.practiceQ.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @Column(name = "doctor_id", nullable = false, length = 500)
    private String doctorId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, length = 500,unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false, length = 100)
    private String role;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @ManyToOne
    @JoinColumn(name = "department_department_id")
    private Department department;

}