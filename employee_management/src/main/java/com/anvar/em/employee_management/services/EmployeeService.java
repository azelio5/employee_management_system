package com.anvar.em.employee_management.services;

import com.anvar.em.employee_management.DTO.UpdateEmployeeRequest;
import com.anvar.em.employee_management.entities.Employee;
import com.anvar.em.employee_management.entities.EmployeeType;
import com.anvar.em.employee_management.exceptions.EmployeeNotFoundException;
import com.anvar.em.employee_management.exceptions.UnableToRegisterEmployeeException;
import com.anvar.em.employee_management.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    //  private Map<String, Employee> employees;
    // private final PasswordEncoder passwordEncoder;

//    public Employee createEmployee(String firstName, String lastName, String password) {
//        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@company.com";
//
//        Employee employee = Employee.builder()
//                .firstName(firstName)
//                .lastName(lastName)
//                .email(email)
//                .password(password)
//                .build();
//        employees.put(email, employee);
//        return employee;
//    }

    public Employee createOrUpdateEmployee(Employee employee) {
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new UnableToRegisterEmployeeException("Unable to create or update employee at this time", e);
        }
    }

    public List<Employee> readAllEmployees() {
        // return employees.values().stream().toList();
        return employeeRepository.findAll();
    }

    public Employee readEmployeeByEmail(String email) {
//        if (!employees.containsKey(email)) {
//            throw new EmployeeNotFoundException();
//        }
//        return employees.get(email);
        return getEmployeeByEmailOrThrowException(email);
    }

    public Employee updateEmployee(UpdateEmployeeRequest request) {
//        if (!employees.containsKey(email)) {
//            throw new EmployeeNotFoundException("Employee to update not found");
//        }
//        Employee persisted;
//        if (!email.equals(updated.getEmail())) {
//            employees.remove(email);
//            employees.put(updated.getEmail(), updated);
//            persisted = employees.get(updated.getEmail());
//        } else {
//            persisted = employees.replace(email, updated);
//        }
//        return persisted;
        Employee employee = getEmployeeByEmailOrThrowException(request.email());

        if (request.firstName() != null) {
            employee.setFirstName(request.firstName());
        }
        if (request.lastName() != null) {
            employee.setLastName(request.lastName());
        }
        if (!request.employeeType().equals(employee.getEmployeeType())) {
            employee.setEmployeeType(request.employeeType());
        }
        if (request.reportsTo() != null) {
            Employee managerOld = employee.getReportsTo();
            Employee manager = getEmployeeByEmailOrThrowException(request.reportsTo());
            employee.setReportsTo(manager);

            updateEmployeeReports(managerOld, employee, "remove");
            updateEmployeeReports(manager, employee, "add");
        }
        if (employee.getEmployeeType().equals(EmployeeType.ADMIN) || employee.getEmployeeType().equals(EmployeeType.MANAGER)) {
            if (employee.getReports() != null) {
                updateEmployeeReports(employee.getReportsTo(), employee, "remove");
                employee.setReports(null);
            }
        }
        employee.setEmail(employee.getFirstName().toLowerCase() + employee.getLastName().toLowerCase() + "@company.com");
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(String email) {
        Employee employee = getEmployeeByEmailOrThrowException(email);
        employeeRepository.delete(employee);
    }

//    public void loadEmployeeData() {
//        employees = new HashMap<>();
//
//        employees.put("admin@company.com", Employee.builder()
//                .firstName("Admin")
//                .lastName("Employee")
//                .email("admin@company.com")
//                .password(passwordEncoder.encode("password"))
//                .build());
//
//        employees.put("manager@company.com", Employee.builder()
//                .firstName("Manager")
//                .lastName("Employee")
//                .email("manager@company.com")
//                .password(passwordEncoder.encode("password"))
//                .build());
//
//        employees.put("employee1@company.com", Employee.builder()
//                .firstName("EmployeeOne")
//                .lastName("EmployeeOne")
//                .email("employee1@company.com")
//                .password(passwordEncoder.encode("password"))
//                .build());
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Employee employee = readEmployeeByEmail(username);
            return User.builder()
                    .username(employee.getEmail())
                    .password(employee.getPassword())
                    .roles(employee.getEmployeeType().toString())
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    private Employee getEmployeeByEmailOrThrowException(String email) {
        return employeeRepository.findEmployeeByEmail(email)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    protected void updateEmployeeReports(Employee manager, Employee report, String operation) {
        List<Employee> reports = manager.getReports();
        if ("add".equals(operation)) {
            reports.add(report);
        } else {
            reports = reports.stream()
                    .filter(employee -> employee.getId() != reports.getId()).toList();
        }
        manager.setReports(reports);
        employeeRepository.save(manager);

    }
}
