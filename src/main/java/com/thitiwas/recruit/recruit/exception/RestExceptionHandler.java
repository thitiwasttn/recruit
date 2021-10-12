package com.thitiwas.recruit.recruit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    // add exception handling code here

    // Add an exception handler using @ExceptionHandler

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(MyResourceNotFoundException exc) {

        return new ResponseEntity<>(ErrorResponse
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exc.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build(), HttpStatus.NOT_FOUND);
    }

    // add another exception handler ... to catch any exception (catch all)

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exc) {

        return new ResponseEntity<>(ErrorResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exc.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
