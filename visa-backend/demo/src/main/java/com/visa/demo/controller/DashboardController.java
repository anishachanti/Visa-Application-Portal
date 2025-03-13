/*package com.visa.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(Authentication authentication) {
        String empId = authentication.getName(); // Get logged-in employee's ID
        return ResponseEntity.ok(Map.of("userId", empId, "message", "Welcome to the Secure Dashboard"));
    }
}

 */




