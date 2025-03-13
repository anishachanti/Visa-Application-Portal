package com.visa.demo.controller;

import com.visa.demo.model.EmployeeDetails;
import com.visa.demo.model.PassportDetails;
import com.visa.demo.model.VisaDetails;
import com.visa.demo.repository.EmployeeDetailsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
public class DController {
    private final EmployeeDetailsRepository employeeRepository;

    public DController(EmployeeDetailsRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<?> getEmployeeDetails(@PathVariable String empId) {
        Optional<EmployeeDetails> employeeOpt = employeeRepository.findById(empId);

        if (employeeOpt.isPresent()) {
            return ResponseEntity.ok(employeeOpt.get());
        } else {
            return ResponseEntity.status(404).body("Employee not found.");
        }
    }

    @GetMapping("/reporting-manager/{managerId}")
    public ResponseEntity<List<EmployeeDetails>> getEmployeesByManager(@PathVariable String managerId) {
        System.out.println("Received Manager ID: " + managerId);

        if (managerId == null || managerId.trim().isEmpty()) {
            System.out.println("❌ ERROR: managerId is NULL or empty!");
            return ResponseEntity.badRequest().build();
        }

        List<EmployeeDetails> employees = employeeRepository.findByReportingManager_EmpId(managerId);

        for (EmployeeDetails emp : employees) {
            System.out.println("Emp ID: " + emp.getEmpId() + ", Name: " + emp.getFirstName() + " " + emp.getLastName());
            System.out.println("Email: " + emp.getEmail());
            System.out.println("Designation: " + emp.getDesignation());

            if (emp.getPassportDetails() == null) {
                emp.setPassportDetails(new PassportDetails()); // Avoid null references
                System.out.println("⚠️ Setting default PassportDetails for Emp ID: " + emp.getEmpId());
            }
            if (emp.getPassportDetails().getVisaDetails() == null) {
                emp.getPassportDetails().setVisaDetails(new VisaDetails()); // Ensure visaDetails exists
                System.out.println("⚠️ Setting default VisaDetails for Emp ID: " + emp.getEmpId());
            }
        }

        return ResponseEntity.ok(employees);
    }
}



