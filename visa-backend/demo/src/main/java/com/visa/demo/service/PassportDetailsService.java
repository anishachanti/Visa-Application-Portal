package com.visa.demo.service;

import com.visa.demo.model.PassportDetails;
import com.visa.demo.repository.PassportDetailsRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PassportDetailsService {
    private final PassportDetailsRepository passportDetailsRepository;

    public PassportDetailsService(PassportDetailsRepository passportDetailsRepository) {
        this.passportDetailsRepository = passportDetailsRepository;
    }

    public Optional<PassportDetails> getPassportByEmployeeId(String empId) {
        return passportDetailsRepository.findByEmployee_EmpId(empId);
    }
}


