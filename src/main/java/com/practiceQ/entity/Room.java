package com.practiceQ.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "admission_date", nullable = false)
    private String admissionDate;

    @Column(name = "total_beds", nullable = false)
    private Integer totalBeds;

    @ManyToOne
    @JoinColumn(name = "patient_patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "staff_staff_id")
    private Staff staff;

}