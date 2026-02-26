package com.anvar.em.employee_management.exceptions;

public class UnableToRegisterEmployeeException extends RuntimeException {

    public UnableToRegisterEmployeeException() {
        super("Unable to register employee");
    }

    public UnableToRegisterEmployeeException(String message) {
        super(message);
    }

    public UnableToRegisterEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }
}
