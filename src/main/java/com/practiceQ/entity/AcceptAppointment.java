package com.practiceQ.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accept_appointment")
public class AcceptAppointment {
    @Id
    @Column(name = "acap_id", nullable = false, length = 500)
    private String acapId;

    @ManyToOne
    @JoinColumn(name = "doctor_doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "appointments_app_id")
    private Appointments appointments;

}