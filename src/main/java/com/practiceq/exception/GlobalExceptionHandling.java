package com.practiceq.exception;

import com.practiceq.payload.ExceptionMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(HospitalNotFoundException.class)
    public ResponseEntity<ExceptionMessageDto> handleHospitalNotFoundException(HospitalNotFoundException exception) {
        ExceptionMessageDto build = ExceptionMessageDto.builder().message(exception.getMessage()).build();
        return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ExceptionMessageDto> handleDepartmentNotFoundException(DepartmentNotFoundException e){
        ExceptionMessageDto build = ExceptionMessageDto.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ExceptionMessageDto> handleDoctorNotFoundException(DoctorNotFoundException e) {
        ExceptionMessageDto build = ExceptionMessageDto.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ExceptionMessageDto> handlePatientNotFoundException(PatientNotFoundException e){
        ExceptionMessageDto build = ExceptionMessageDto.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(build,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity<ExceptionMessageDto> handleInvoiceNotFoundException(InvoiceNotFoundException e) {
        ExceptionMessageDto build = ExceptionMessageDto.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ExceptionMessageDto> handleAppointmentNotFoundException(AppointmentNotFoundException e) {
        ExceptionMessageDto build = ExceptionMessageDto.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StaffNotFoundException.class)
    public ResponseEntity<ExceptionMessageDto> handleStaffNotFoundException(StaffNotFoundException e) {
        ExceptionMessageDto build = ExceptionMessageDto.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(TokenExpirationException.class)
    public ResponseEntity<ExceptionMessageDto> handleTokenExpirationException(TokenExpirationException e){
        ExceptionMessageDto build = ExceptionMessageDto.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(build, HttpStatus.UNAUTHORIZED);
    }

}

