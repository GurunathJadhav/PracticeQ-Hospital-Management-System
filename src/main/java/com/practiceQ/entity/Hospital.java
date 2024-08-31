package com.practiceQ.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hospital")
public class Hospital {
    @Id
    @Column(name = "hospital_id", nullable = false, length = 500)
    private String hospitalId;

    @NotEmpty(message = "Hospital Name must not be empty")
    @Column(name = "hospital_name", nullable = false)
    private String hospitalName;

    @NotEmpty(message = "State must not be empty")
    @Column(name = "state", nullable = false)
    private String state;

    @NotEmpty(message = "Zip code must not be empty")
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    //    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Column(name = "phone_number", nullable = false)
    private Long phoneNumber;

}
