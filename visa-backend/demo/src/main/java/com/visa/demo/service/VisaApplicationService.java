package com.visa.demo.service;

import com.visa.demo.model.Employee;
import com.visa.demo.model.VisaApplication;
import com.visa.demo.repository.EmployeeRepository;
import com.visa.demo.repository.VisaApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VisaApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(VisaApplicationService.class);
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
            System.out.println("✅ Visa Application saved for Employee: " + employee.getName());


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
    public boolean updateApplicationStatus(String loggedInUserId, String empId, String status) {
        Optional<Employee> loggedInUserOpt = employeeRepository.findByEmpId(loggedInUserId);

        if (loggedInUserOpt.isEmpty()) {
            return false; // Unauthorized user
        }

        Employee loggedInUser = loggedInUserOpt.get();
        Optional<Employee> employeeOpt = employeeRepository.findByEmpId(empId);
        Optional<VisaApplication> visaOpt = visaApplicationRepository.findById(empId);

        if (employeeOpt.isEmpty() || visaOpt.isEmpty()) {
            return false; // Employee or Visa Application not found
        }

        Employee employee = employeeOpt.get();
        VisaApplication visaApplication = visaOpt.get();

        // ✅ Allow both Reporting Managers & Visa Team to update status
        if (loggedInUser.getEmpId().equals(employee.getReportingManagerId()) ||
                "VISA_TEAM".equals(loggedInUser.getRole())) {

            visaApplication.setStatus(status);

            /*if ("APPROVED".equalsIgnoreCase(status)) {
                visaApplication.setDateApproved(LocalDate.now());  // Set current date as approval date
            } else {
                visaApplication.setDateApproved(null);  // Reset approval date for other statuses
            }

             */

            if ("APPROVED".equalsIgnoreCase(status)) {
                if (visaApplication.getDateApproved() == null) {  // ✅ Only set the date if it's not already set
                    visaApplication.setDateApproved(LocalDate.now());
                }
            }
// ❌ Do NOT reset the date when status changes (leave it unchanged)

            /*if ("Petition Approved".equalsIgnoreCase(status) || "Petition Rejected".equalsIgnoreCase(status)) {
                visaApplicationRepository.delete(visaApplication);
                return true; // ✅ Successfully deleted, no need to save
            }

             */



            visaApplicationRepository.save(visaApplication);

            // ✅ Notify the employee about the status change
            emailService.sendEmail(employee.getEmail(),
                    "Visa Status Update",
                    "Dear " + employee.getName() + ",\n\nYour visa application status has been updated to: " + status);

            return true;
        }

        return false; // Unauthorized action
    }
}






