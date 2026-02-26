package com.anvar.em.employee_management.DTO;

import lombok.Builder;

@Builder
public record LoginRequest(String email, String password) {
}
