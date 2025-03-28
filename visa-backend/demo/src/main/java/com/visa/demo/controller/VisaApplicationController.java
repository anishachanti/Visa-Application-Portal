package com.visa.demo.controller;

import com.visa.demo.model.VisaApplication;
import com.visa.demo.repository.VisaApplicationRepository;
import com.visa.demo.service.VisaApplicationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/visa")
public class VisaApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(VisaApplicationController.class);
    private final VisaApplicationService visaApplicationService;
    private final VisaApplicationRepository visaApplicationRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public VisaApplicationController(VisaApplicationService visaApplicationService, VisaApplicationRepository visaApplicationRepository) {
        this.visaApplicationService = visaApplicationService;
        this.visaApplicationRepository = visaApplicationRepository;
    }


    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> applyForVisa(
            @RequestPart("visaApplication") VisaApplication visaApplication,
            @RequestPart("passportFile") MultipartFile passportFile,
            @RequestPart(value = "msDegreeFile", required = false) MultipartFile degreeFile) {

        System.out.println("🔹 Received Visa Application Request: " + visaApplication);

        String uploadDir = this.uploadDir; // ✅ Use value from application.properties


        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();  // Creates the uploads directory if it doesn't exist
        }

        // Save Passport PDF
        if (passportFile != null && !passportFile.isEmpty()) {
            String passportFileName = visaApplication.getEmpId() + "_" + visaApplication.getFullName().replaceAll("\\s+", "") + ".pdf";
            try {
                File passportDest = new File(uploadDir + File.separator + passportFileName);
                passportFile.transferTo(passportDest);
                visaApplication.setPassportFilePath(passportDest.getAbsolutePath());

                System.out.println("Passport saved at: " + passportDest.getAbsolutePath());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Failed to upload passport.");
            }
        }

        // Save Master's Degree PDF
        if (degreeFile != null && !degreeFile.isEmpty()) {
            String degreeFileName = visaApplication.getEmpId() + "_" + visaApplication.getFullName().replaceAll("\\s+", "") + "_MasterDegree.pdf";
            try {
                File degreeDest = new File(uploadDir + File.separator + degreeFileName);
                degreeFile.transferTo(degreeDest);
                visaApplication.setDegreeFilePath(degreeDest.getAbsolutePath());
                System.out.println("Degree saved at: " + degreeDest.getAbsolutePath());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Failed to upload degree certificate.");
            }
        }

        // Save the visa application
        VisaApplication savedApplication = visaApplicationService.submitApplication(visaApplication);
        if (savedApplication == null) {
            System.out.println("❌ Error: Visa application submission failed.");
            return ResponseEntity.badRequest().body("❌ Error: Visa application submission failed.");
        }
        System.out.println("✅ Visa application submitted successfully for Employee ID: " + savedApplication.getEmpId());
        return ResponseEntity.ok("✅ Visa application submitted successfully for Employee ID: " + savedApplication.getEmpId());
    }

    @GetMapping("/manager-applications")
    public ResponseEntity<List<VisaApplication>> getPendingApplications(Authentication authentication) {
        String managerId = authentication.getName(); // ✅ Get the logged-in manager's ID
        List<VisaApplication> applications = visaApplicationService.getApplicationsForManager(managerId);
        return ResponseEntity.ok(applications);
    }

    // ✅ Change 5: Search applications by Employee ID
    @GetMapping("/search")
    public ResponseEntity<List<VisaApplication>> searchApplicationsByEmpId(@RequestParam String empId) {
        List<VisaApplication> applications = visaApplicationRepository.searchByEmployeeId(empId);
        return ResponseEntity.ok(applications);
    }

    /*@GetMapping("/visa-team-applications")
    public ResponseEntity<List<VisaApplication>> getVisaTeamApplications() {
        List<VisaApplication> approvedApplications = visaApplicationRepository.findApprovedApplications();
        System.out.println("✅ Approved Applications Count: " + approvedApplications.size());

        if (approvedApplications.isEmpty()) {
            System.out.println("❌ No approved applications found!");
        }
        return ResponseEntity.ok(approvedApplications);
    }
     */

    @GetMapping("/visa-team-applications")
    public ResponseEntity<List<VisaApplication>> getVisaTeamApplications() {
        List<VisaApplication> inProgressApplications = visaApplicationRepository.findInProgressApplications();

        logger.debug("Fetched {} applications from database:", inProgressApplications.size());
        for (VisaApplication app : inProgressApplications) {
            logger.debug("Application: empId={}, status={}", app.getEmpId(), app.getStatus());
        }
        return ResponseEntity.ok(inProgressApplications);
    }





    // ✅ New API: Update Visa Application Status (Approve/Reject)
    @PutMapping("/update-status/{empId}")
    public ResponseEntity<String> updateApplicationStatus(
            Authentication authentication,
            @PathVariable String empId,
            @RequestParam String status) {

        String loggedInUserId = authentication.getName(); // ✅ Get the logged-in manager's ID
        boolean updated = visaApplicationService.updateApplicationStatus(loggedInUserId, empId, status);

        if (updated) {
            return ResponseEntity.ok("✅ Status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("❌ You are not authorized to update this application.");
        }
    }
}





