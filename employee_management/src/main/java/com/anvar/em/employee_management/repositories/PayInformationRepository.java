package com.anvar.em.employee_management.repositories;

import com.anvar.em.employee_management.entities.PayInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayInformationRepository extends JpaRepository<PayInformation, Integer> {
}
