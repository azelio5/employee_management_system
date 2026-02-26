package com.anvar.em.employee_management.DTO;

import com.anvar.em.employee_management.entities.Employee;
import com.anvar.em.employee_management.entities.EmployeeType;
import lombok.Builder;

@Builder
public record UpdateEmployeeRequest(String email, EmployeeType employeeType, String firstName, String lastName, String reportsTo){

}
