package com.visa.demo.repository;

import com.visa.demo.model.PassportDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PassportDetailsRepository extends JpaRepository<PassportDetails, String> {
    Optional<PassportDetails> findByEmployee_EmpId(String empId);
}



