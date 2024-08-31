package com.practiceQ.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import jakarta.persistence.ElementCollection;

@Getter
@Setter
@Entity
@Table(name = "prescription")
public class Prescription {
    @Id
    @Column(name = "prescription_id", nullable = false)
    private String prescriptionId;

    @ElementCollection
    @CollectionTable(name = "prescription_medicines", joinColumns = @JoinColumn(name = "prescription_id"))
    @Column(name = "medicine_name", nullable = false)
    private List<String> medicines;

    @Column(name = "duration", nullable = false)
    private String duration;

    @ManyToOne
    @JoinColumn(name = "patient_patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_doctor_id")
    private Doctor doctor;
}
