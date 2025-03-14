package com.visa.demo.service;

import com.visa.demo.model.Employee;
import com.visa.demo.model.VisaApplication;
import com.visa.demo.repository.EmployeeRepository;
import com.visa.demo.repository.VisaApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VisaApplicationService {

    private final VisaApplicationRepository visaApplicationRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;

    public VisaApplicationService(VisaApplicationRepository visaApplicationRepository, EmployeeRepository employeeRepository, EmailService emailService) {
        this.visaApplicationRepository = visaApplicationRepository;
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
    }

    @Transactional
    public VisaApplication submitApplication(VisaApplication application) {
        application.setStatus("PENDING"); // ✅ Set initial status to PENDING
        VisaApplication savedApplication = visaApplicationRepository.save(application);

        // ✅ Fetch employee details
        Optional<Employee> employeeOpt = employeeRepository.findByEmpId(application.getEmpId());
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();

            // ✅ Send email to the employee
            emailService.sendEmail(
                    employee.getEmail(),
                    "Visa Application Submitted Successfully",
                    "Dear " + employee.getName() + ",\n\nYour visa application has been successfully submitted."
            );

            // ✅ Notify reporting manager (if assigned)
            if (employee.getReportingManagerId() != null) {
                Optional<Employee> managerOpt = employeeRepository.findByEmpId(employee.getReportingManagerId());
                managerOpt.ifPresent(manager -> emailService.sendEmail(
                        manager.getEmail(),
                        "New Visa Application Submitted",
                        "Dear " + manager.getName() + ",\n\nA new visa application has been submitted by " + employee.getName() + ". Please review and approve."
                ));
            }
        }

        return savedApplication;
    }


    public List<VisaApplication> getApplicationsForManager(String managerId) {
        return visaApplicationRepository.findByManagerId(managerId);
    }


    @Transactional
    public boolean updateApplicationStatus(String managerId, String empId, String status) {
        Optional<Employee> employeeOpt = employeeRepository.findByEmpId(empId);

        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();

            // ✅ Check if the reporting manager ID matches the logged-in manager ID
            if (employee.getReportingManagerId() == null || !employee.getReportingManagerId().equals(managerId)) {
                System.out.println("❌ Unauthorized attempt to update status by Manager ID: " + managerId);
                return false; // Unauthorized action
            }

            // ✅ Fetch and update the application status
            Optional<VisaApplication> optionalApplication = visaApplicationRepository.findById(empId);
            if (optionalApplication.isPresent()) {
                VisaApplication application = optionalApplication.get();
                application.setStatus(status);
                visaApplicationRepository.save(application);

                // ✅ Send email notification to employee
                emailService.sendEmail(
                        employee.getEmail(),
                        "Visa Application " + status,
                        "Dear " + employee.getName() + ",\n\nYour visa application has been " + status.toLowerCase() + "."
                );

                return true;
            }
        }

        return false;
    }
}

 




