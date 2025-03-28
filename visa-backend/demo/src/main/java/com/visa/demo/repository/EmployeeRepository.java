package com.visa.demo.repository;

import com.visa.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByEmpId(String empId);
    List<Employee> findByReportingManagerId(String reportingManagerId);

    boolean existsByReportingManagerId(String reportingManagerId);

    @Query("SELECT e FROM Employee e WHERE e.role = :role")
    List<Employee> findByRole(@Param("role") String role);



}





