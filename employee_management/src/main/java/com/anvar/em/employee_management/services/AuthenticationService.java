package com.anvar.em.employee_management.services;

import com.anvar.em.employee_management.DTO.RegisterRequest;
import com.anvar.em.employee_management.DTO.RegisterResponse;
import com.anvar.em.employee_management.entities.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final EmployeeService employeeService;
    private final ContactInformationService contactInformationService;
    private final PayInformationService payInformationService;
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

    public RegisterResponse registerEmployee(RegisterRequest registerRequest){
        ContactInformation employeeContactInformation = contactInformationService.createContactInformation(
                ContactInformation.builder()
                        .phoneNumber(registerRequest.phoneNumber())
                        .build()
        );
        PayInformation employeePayInformation = payInformationService.createPayInformation(
                PayInformation.builder()
                        .payAmount(registerRequest.payAmount())
                        .payType(registerRequest.payType())
                        .build()
        );
        String email = registerRequest.firstName().toLowerCase() + registerRequest.lastName().toLowerCase() + "@company.com";

        String temporaryPassword = generateTemporaryPassword();

        Employee manager = registerRequest.reportTo() != null
                ? employeeService.readEmployeeByEmail(registerRequest.reportTo())
                : null;
        Employee employee = employeeService.createOrUpdateEmployee(
                Employee.builder()
                        .employeeType(registerRequest.employeeType())
                        .firstName(registerRequest.firstName())
                        .lastName(registerRequest.lastName())
                        .email(email)
                        .password(passwordEncoder.encode(temporaryPassword))
                        .contactInformation(employeeContactInformation)
                        .payInformation(employeePayInformation)
                        .firstTimeLogin(true)
                        .reportsTo(manager)
                        .reports(new ArrayList<>())
                        .build()
        );
        return RegisterResponse.builder()
                .employee(employee)
                .temporaryPassword(temporaryPassword)
                .build();
    }

    public void loadUserInformation(){
        ContactInformation contactInformation1 = contactInformationService.createContactInformation(
                ContactInformation.builder()
                        .phoneNumber("11111111111")
                        .addressLine1("123 Admin road")
                        .city("Work Town")
                        .state("MOS")
                        .zipCode("123456")
                        .build()
        );
        ContactInformation contactInformation2 = contactInformationService.createContactInformation(
                ContactInformation.builder()
                        .phoneNumber("22222222222")
                        .addressLine1("345 Manager avenue")
                        .city("Meeting Central")
                        .state("MOS")
                        .zipCode("234567")
                        .build()
        );
        ContactInformation contactInformation3 = contactInformationService.createContactInformation(
                ContactInformation.builder()
                        .phoneNumber("33333333333")
                        .addressLine1("678 Hourly street")
                        .addressLine2("Apt 101")
                        .city("Zvenigorod")
                        .state("MO")
                        .zipCode("345678")
                        .build()
        );
        PayInformation payInformation1 = payInformationService.createPayInformation(
                PayInformation.builder()
                        .payType(PayType.SALARY)
                        .payAmount(new BigDecimal("80000.00"))
                        .build()
        );
        PayInformation payInformation2 = payInformationService.createPayInformation(
                PayInformation.builder()
                        .payType(PayType.SALARY)
                        .payAmount(new BigDecimal("100000.00"))
                        .build()
        );
        PayInformation payInformation3 = payInformationService.createPayInformation(
                PayInformation.builder()
                        .payType(PayType.HOURLY)
                        .payAmount(new BigDecimal("25.00"))
                        .build()
        );

        Employee admin = employeeService.createOrUpdateEmployee(
                Employee.builder()
                        .employeeType(EmployeeType.ADMIN)
                        .contactInformation(contactInformation1)
                        .payInformation(payInformation1)
                        .firstName("Admin")
                        .lastName("Employee")
                        .email("admin@company.com")
                        .password(passwordEncoder.encode("password"))
                        .firstTimeLogin(false)
                        .reportsTo(null)
                        .reports(new ArrayList<>())
                        .build()
        );
        Employee manager = employeeService.createOrUpdateEmployee(
                Employee.builder()
                        .employeeType(EmployeeType.MANAGER)
                        .contactInformation(contactInformation2)
                        .payInformation(payInformation2)
                        .firstName("Manager")
                        .lastName("Employee")
                        .email("manager@company.com")
                        .password(passwordEncoder.encode("password"))
                        .firstTimeLogin(false)
                        .reportsTo(null)
                        .reports(new ArrayList<>())
                        .build()
        );

        Employee employee = employeeService.createOrUpdateEmployee(
                Employee.builder()
                        .employeeType(EmployeeType.EMPLOYEE)
                        .contactInformation(contactInformation3)
                        .payInformation(payInformation3)
                        .firstName("Employee")
                        .lastName("One")
                        .email("employee1@company.com")
                        .password(passwordEncoder.encode("password"))
                        .firstTimeLogin(false)
                        .reportsTo(manager)
                        .reports(new ArrayList<>())
                        .build()
        );

        employeeService.updateEmployeeReports(manager, employee, "add");
    }

    private String generateTemporaryPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

}
