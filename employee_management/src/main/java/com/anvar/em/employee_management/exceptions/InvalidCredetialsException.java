package com.anvar.em.employee_management.exceptions;

public class InvalidCredetialsException extends RuntimeException {

    public InvalidCredetialsException() {
        super("Invalid Credentials");
    }

    public InvalidCredetialsException(String message) {
        super(message);
    }
}
