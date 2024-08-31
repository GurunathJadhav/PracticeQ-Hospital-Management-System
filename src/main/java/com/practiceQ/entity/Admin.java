package com.practiceQ.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "admin_id", nullable = false)
    private String adminId;

    @NotEmpty(message = "Must not be empty")
    @Size(min = 3,message = "First name must be at least 3 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "Must not be empty")
    @Size(min = 3,message = "Last name must be at least 3 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty(message = "Must not be empty")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "Must not be empty")
    @Size(min=5,message = "Password must be at least 5 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;



}