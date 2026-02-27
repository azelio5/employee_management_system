package com.anvar.em.employee_management;

import com.anvar.em.employee_management.services.AuthenticationService;
import com.anvar.em.employee_management.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AuthenticationService authenticationService) {
        return args -> {
        //    employeeService.loadEmployeeData();
            authenticationService.loadUserInformation();
        };
    }
}
