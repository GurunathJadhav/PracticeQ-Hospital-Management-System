package com.practiceq.exception;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String appointmentNotFound) {
        super(appointmentNotFound);
    }
}
