package com.visa.demo.controller;

import com.visa.demo.model.VisaDetails;
import com.visa.demo.model.PassportDetails;
import com.visa.demo.repository.VisaDetailsRepository;
import com.visa.demo.repository.PassportDetailsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/visa")
public class VisaAppController {
    private final VisaDetailsRepository visaDetailsRepository;
    private final PassportDetailsRepository passportDetailsRepository;

    public VisaAppController(VisaDetailsRepository visaDetailsRepository, PassportDetailsRepository passportDetailsRepository) {
        this.visaDetailsRepository = visaDetailsRepository;
        this.passportDetailsRepository = passportDetailsRepository;
    }

    @PostMapping("/apply/{passportNo}")
    public ResponseEntity<?> applyForVisa(@PathVariable String passportNo, @RequestBody VisaDetails visaDetails) {
        System.out.println("Received Visa Application for Passport: " + passportNo);
        System.out.println("Visa Details: " + visaDetails);


        Optional<PassportDetails> passportOpt = passportDetailsRepository.findById(passportNo);

        if (passportOpt.isPresent()) {
            System.out.println("✅ Passport Found: " + passportOpt.get());
            visaDetails.setPassport(passportOpt.get());
            visaDetails.setManagerApprovalStatus("PENDING");
            visaDetails.setVisaStatus("IN PROCESS");

            System.out.println("🔹 Visa Before Save: " + visaDetails);

            VisaDetails savedVisa = visaDetailsRepository.save(visaDetails);// ✅ Save and return
            System.out.println("Saved Visa Application: " + savedVisa);
            return ResponseEntity.ok(savedVisa); // ✅ Return full object including visaId
        } else {
            System.out.println("❌ Passport Not Found for Number: " + passportNo);
            return ResponseEntity.status(404).body("Passport details not found.");
        }
    }

    @GetMapping("/status/{passportNo}")
    public ResponseEntity<List<VisaDetails>> getVisaStatus(@PathVariable String passportNo) {
        System.out.println("🔹 Fetching Visa Status for Passport: " + passportNo);

        List<VisaDetails> visaDetails = visaDetailsRepository.findByPassport_PassportNo(passportNo);

        if (visaDetails.isEmpty()) {
            System.out.println("❌ No Visa Applications Found for Passport: " + passportNo);
        } else {
            System.out.println("✅ Retrieved Visa Applications: " + visaDetails);
        }

        return ResponseEntity.ok(visaDetails);
    }

    @PutMapping("/update-status/{visaId}/{status}")
    public ResponseEntity<?> updateVisaStatus(@PathVariable Long visaId, @PathVariable String status) {
        System.out.println("🔹 Request to Update Visa Status - Visa ID: " + visaId + ", New Status: " + status);

        Optional<VisaDetails> visaDetailsOpt = visaDetailsRepository.findById(visaId);

        if (visaDetailsOpt.isPresent()) {
            VisaDetails visaDetails = visaDetailsOpt.get();
            System.out.println("✅ Found Visa for Update: " + visaDetails);
            visaDetails.setVisaStatus(status);

            if (status.equalsIgnoreCase("APPROVED")) {
                visaDetails.setManagerApprovalStatus("APPROVED");
            } else if (status.equalsIgnoreCase("REJECTED")) {
                visaDetails.setManagerApprovalStatus("REJECTED");
            }

            visaDetailsRepository.save(visaDetails);
            System.out.println("✅ Visa Status Updated: " + visaDetails);

            return ResponseEntity.ok("Visa status updated.");
        } else {
            System.out.println("❌ Visa Application Not Found for ID: " + visaId);
            return ResponseEntity.status(404).body("Visa application not found.");
        }
    }
}


