package com.anvar.em.employee_management.services;

import com.anvar.em.employee_management.entities.Employee;
import com.anvar.em.employee_management.exceptions.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService implements UserDetailsService {
    private Map<String, Employee> employees;
    private final PasswordEncoder passwordEncoder;

    public Employee createEmployee(String firstName, String lastName, String password) {
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@company.com";

        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build();
        employees.put(email, employee);
        return employee;
    }

    public List<Employee> readAllEmployees() {
        return employees.values().stream().toList();
    }

    public Employee readEmployeeByEmail(String email) {
        if (!employees.containsKey(email)) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(email);
    }

    public Employee updateEmployee(String email, Employee updated) {
        if (!employees.containsKey(email)) {
            throw new EmployeeNotFoundException("Employee to update not found");
        }
        Employee persisted;
        if (!email.equals(updated.getEmail())) {
            employees.remove(email);
            employees.put(updated.getEmail(), updated);
            persisted = employees.get(updated.getEmail());
        } else {
            persisted = employees.replace(email, updated);
        }
        return persisted;
    }

    public void deleteEmployee(String email) {
        Employee deletedEmployee = employees.remove(email);
        if (deletedEmployee == null) {
            throw new EmployeeNotFoundException("Employee to delete not found");
        }
    }

    public void loadEmployeeData() {
        employees = new HashMap<>();

        employees.put("admin@company.com", Employee.builder()
                .firstName("Admin")
                .lastName("Employee")
                .email("admin@company.com")
                .password("password")
                .build());

        employees.put("manager@company.com", Employee.builder()
                .firstName("Manager")
                .lastName("Employee")
                .email("manager@company.com")
                .password("password")
                .build());

        employees.put("employee1@company.com", Employee.builder()
                .firstName("EmployeeOne")
                .lastName("EmployeeOne")
                .email("employee1@company.com")
                .password("password")
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Employee employee = readEmployeeByEmail(username);
            return User.builder()
                    .username(employee.getEmail())
                    .password(employee.getPassword())
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
