package com.visa.demo.service;

import com.visa.demo.model.VisaDetails;
import com.visa.demo.repository.VisaDetailsRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VisaDetailsService {
    private final VisaDetailsRepository visaDetailsRepository;

    public VisaDetailsService(VisaDetailsRepository visaDetailsRepository) {
        this.visaDetailsRepository = visaDetailsRepository;
    }

    public List<VisaDetails> getVisaByPassportNo(String passportNo) {
        return visaDetailsRepository.findByPassport_PassportNo(passportNo);
    }
}


