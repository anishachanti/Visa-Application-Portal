package com.visa.demo.repository;

import com.visa.demo.model.VisaDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisaDetailsRepository extends JpaRepository<VisaDetails, Long> {
    List<VisaDetails> findByPassport_PassportNo(String passportNo);
}


