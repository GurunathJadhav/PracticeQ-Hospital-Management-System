package com.practiceQ.exception;

public class StaffNotFoundException extends RuntimeException {
    public StaffNotFoundException(String staffNotFound) {
        super(staffNotFound);
    }
}
