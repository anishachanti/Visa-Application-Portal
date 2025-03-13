package com.visa.demo.service;

import com.visa.demo.model.EducationDetails;
import com.visa.demo.repository.EducationDetailsRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EducationDetailsService {
    private final EducationDetailsRepository educationDetailsRepository;

    public EducationDetailsService(EducationDetailsRepository educationDetailsRepository) {
        this.educationDetailsRepository = educationDetailsRepository;
    }

    public List<EducationDetails> getEducationByEmployeeId(String empId) {
        return educationDetailsRepository.findByEmployee_EmpId(empId);
    }
}


