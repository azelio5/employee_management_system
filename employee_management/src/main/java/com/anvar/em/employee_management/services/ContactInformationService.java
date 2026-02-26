package com.anvar.em.employee_management.services;


import com.anvar.em.employee_management.entities.ContactInformation;
import com.anvar.em.employee_management.repositories.ContactInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactInformationService {

    private final ContactInformationRepository contactInformationRepository;

    public ContactInformation createContactInformation(ContactInformation contactInformation) {
        return contactInformationRepository.save(contactInformation);
    }

}
