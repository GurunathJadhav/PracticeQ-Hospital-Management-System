package com.practiceq.exception;

public class StaffNotFoundException extends RuntimeException {
    public StaffNotFoundException(String staffNotFound) {
        super(staffNotFound);
    }
}
