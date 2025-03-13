package com.visa.demo.controller;

import com.visa.demo.model.PassportDetails;
import com.visa.demo.repository.PassportDetailsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/passport")
public class PassportDetailsController {
    private final PassportDetailsRepository passportRepository;

    public PassportDetailsController(PassportDetailsRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPassport(@RequestBody PassportDetails passport) {
        passportRepository.save(passport);
        return ResponseEntity.ok("Passport details added.");
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<?> getPassportByEmployee(@PathVariable String empId) {
        Optional<PassportDetails> passportOpt = passportRepository.findByEmployee_EmpId(empId);

        if (passportOpt.isPresent()) {
            return ResponseEntity.ok(passportOpt.get());
        } else {
            return ResponseEntity.status(404).body("Passport not found.");
        }
    }
}


