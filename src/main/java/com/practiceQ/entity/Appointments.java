package com.practiceQ.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "appointments")
public class Appointments {
    @Id
    @Column(name = "app_id", nullable = false, length = 500)
    private String appId;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "reason", nullable = false, length = 500)
    private String reason;

    @Column(name = "emergency", nullable = false)
    private Boolean emergency;

    @ManyToOne
    @JoinColumn(name = "doctor_doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_patient_id")
    private Patient patient;

}