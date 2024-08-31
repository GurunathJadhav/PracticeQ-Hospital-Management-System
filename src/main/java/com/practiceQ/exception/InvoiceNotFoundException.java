package com.practiceQ.exception;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException(String s) {
        super(s);
    }
}
