package com.anvar.em.employee_management.DTO;

import com.anvar.em.employee_management.entities.Employee;
import lombok.Builder;

@Builder
public record UpdateEmployeeRequest(String email, Employee employee){}
