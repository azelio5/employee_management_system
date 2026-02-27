package com.anvar.em.employee_management.DTO;

import com.anvar.em.employee_management.entities.EmployeeType;
import com.anvar.em.employee_management.entities.PayType;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RegisterRequest(
        EmployeeType employeeType,
        String firstName,
        String lastName,
        String phoneNumber,
        PayType payType,
        BigDecimal payAmount,
        String reportTo
) {
}
