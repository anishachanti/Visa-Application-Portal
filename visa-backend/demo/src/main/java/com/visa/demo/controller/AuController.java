/*package com.visa.demo.controller;

import com.visa.demo.model.EmployeeDetails;
import com.visa.demo.model.LoginRequest;
import com.visa.demo.repository.EmployeeDetailsRepository;
import com.visa.demo.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuController {
    private final AuthenticationManager authenticationManager;
    private final EmployeeDetailsRepository employeeRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuController(AuthenticationManager authenticationManager, EmployeeDetailsRepository employeeRepository,
                        JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.employeeRepository = employeeRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getEmpId() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Employee ID and Password are required");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmpId(), loginRequest.getPassword())
            );

            Optional<EmployeeDetails> employeeOpt = employeeRepository.findByEmpId(loginRequest.getEmpId());

            if (employeeOpt.isPresent()) {
                EmployeeDetails employee = employeeOpt.get();
                String role = (employee.getReportingManager() == null) ? "MANAGER" : "EMPLOYEE";

                String token = jwtUtil.generateToken(employee.getEmpId());

                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "role", role,
                        "empId", employee.getEmpId()
                ));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(401).body("❌ Invalid Employee ID or Password.");
        }

        return ResponseEntity.status(401).body("❌ Authentication failed.");
    }

    // ✅ Register New Employee
    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeDetails employee) {
        if (employeeRepository.findByEmpId(employee.getEmpId()).isPresent()) {
            return ResponseEntity.badRequest().body("❌ Employee ID already exists.");
        }

        // Hash the password before saving
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        employeeRepository.save(employee);
        return ResponseEntity.ok("✅ Employee registered successfully.");
    }
}
*/




