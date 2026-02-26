package com.anvar.em.employee_management.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid Credentials");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
