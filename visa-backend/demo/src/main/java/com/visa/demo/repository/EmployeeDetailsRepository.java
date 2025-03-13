package com.visa.demo.repository;

import com.visa.demo.model.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, String> {
    Optional<EmployeeDetails> findByEmpId(String empId);
    boolean existsByReportingManager_EmpId(String managerId);

    @Query("SELECT e FROM EmployeeDetails e WHERE e.reportingManager.empId = :managerId")
    List<EmployeeDetails> findByReportingManager_EmpId(String managerId);
}


