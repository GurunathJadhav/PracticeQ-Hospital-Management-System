package com.practiceq.exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(String s) {
        super(s);
    }
}
