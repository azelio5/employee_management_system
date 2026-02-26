package com.anvar.em.employee_management.repositories;

import com.anvar.em.employee_management.entities.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactInformationRepository extends JpaRepository<ContactInformation, Integer> {
}
