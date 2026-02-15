package com.anvar.em.employee_management.exceptions;

import com.anvar.em.employee_management.entities.Employee;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException() {
        super("Employee Not Found");
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
