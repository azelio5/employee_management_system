package com.anvar.em.employee_management.controllers;

import com.anvar.em.employee_management.DTO.LoginRequest;
import com.anvar.em.employee_management.entities.Employee;
import com.anvar.em.employee_management.exceptions.InvalidCredentialsException;
import com.anvar.em.employee_management.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public Employee loginEmployee(@RequestBody LoginRequest body, HttpServletResponse response, HttpServletRequest request) {
        return authenticationService.loginEmployee(body.email(), body.password(), request, response);

    }

    @ExceptionHandler({InvalidCredentialsException.class})
    public ResponseEntity<String> handleInvalidCredentialsException() {
        return ResponseEntity.status(403).body("Invalid credentials");
    }

}
