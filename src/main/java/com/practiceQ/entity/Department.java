package com.practiceQ.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "department")
public class Department {
    @Id
    @Column(name = "department_id", nullable = false, length = 500)
    private String departmentId;

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @ManyToOne
    @JoinColumn(name = "hospital_hospital_id")
    private Hospital hospital;



}