package com.anvar.em.employee_management.services;

import com.anvar.em.employee_management.entities.PayInformation;
import com.anvar.em.employee_management.repositories.PayInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PayInformationService {

    private final PayInformationRepository payInformationRepository;

    public PayInformation createPayInformation(PayInformation payInformation) {
        return payInformationRepository.save(payInformation);
    }
}
