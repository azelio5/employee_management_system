package com.anvar.em.employee_management.repositories;

import com.anvar.em.employee_management.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findEmployeeByEmail(String email);
}
