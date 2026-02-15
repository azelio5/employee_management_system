package com.anvar.em.employee_management.DTO;

import lombok.Builder;

@Builder
public record CreateEmployeeRequest(String firstName, String lastName,String password) {};
