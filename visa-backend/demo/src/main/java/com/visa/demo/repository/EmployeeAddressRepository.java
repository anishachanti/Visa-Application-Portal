package com.visa.demo.repository;

import com.visa.demo.model.EmployeeAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress, Long> {
    List<EmployeeAddress> findByEmployee_EmpId(String empId);
}



