package com.anvar.em.employee_management.services;

import com.anvar.em.employee_management.entities.Employee;
import com.anvar.em.employee_management.exceptions.InvalidCredentialsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final EmployeeService employeeService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();

    public Employee loginEmployee(String email, String password,
                                  HttpServletRequest request, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken
                    = UsernamePasswordAuthenticationToken.unauthenticated(email, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            if (authentication.isAuthenticated()) {
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authentication);
                securityContextHolderStrategy.setContext(context);
                securityContextRepository.saveContext(context, request, response);

            }
            return employeeService.readEmployeeByEmail(email);

        } catch (Exception e) {
            throw new InvalidCredentialsException("Username or password is incorrect");
        }
    }

}
