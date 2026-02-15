package com.anvar.em.employee_management.controllers;

import com.anvar.em.employee_management.DTO.CreateEmployeeRequest;
import com.anvar.em.employee_management.DTO.UpdateEmployeeRequest;
import com.anvar.em.employee_management.entities.Employee;
import com.anvar.em.employee_management.exceptions.EmployeeNotFoundException;
import com.anvar.em.employee_management.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/")
    public Employee postEmployee(@RequestBody CreateEmployeeRequest body) {
        return employeeService.createEmployee(body.firstName(), body.lastName(), body.lastName());
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.readAllEmployees();
    }

    @GetMapping("/email/{email}")
    public Employee getEmployeeByEmail(@PathVariable String email) {
        return employeeService.readEmployeeByEmail(email);
    }

    @PutMapping("/")
    public Employee updateEmployee(@RequestBody UpdateEmployeeRequest body) {
        return employeeService.updateEmployee(body.email(), body.employee());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String email) {
        employeeService.deleteEmployee(email);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({EmployeeNotFoundException.class})
    public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
        return ResponseEntity.status(400).body("Unable to handle request: " + e.getMessage());
    }

}
