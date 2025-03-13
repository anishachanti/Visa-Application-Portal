package com.visa.demo.controller;

import com.visa.demo.model.EducationDetails;
import com.visa.demo.repository.EducationDetailsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education")
public class EducationDetailsController {
    private final EducationDetailsRepository educationRepository;

    public EducationDetailsController(EducationDetailsRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEducation(@RequestBody EducationDetails education) {
        educationRepository.save(education);
        return ResponseEntity.ok("Education details added.");
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<EducationDetails>> getEducationDetails(@PathVariable String empId) {
        List<EducationDetails> educationDetails = educationRepository.findByEmployee_EmpId(empId);
        return ResponseEntity.ok(educationDetails);
    }
}


