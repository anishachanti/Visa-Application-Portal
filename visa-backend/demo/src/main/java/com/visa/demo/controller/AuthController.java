package com.visa.demo.controller;

import com.visa.demo.model.LoginRequest;
import com.visa.demo.model.Employee;
import com.visa.demo.repository.EmployeeRepository;
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
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, EmployeeRepository employeeRepository,
                          JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.employeeRepository = employeeRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getEmpId() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Employee ID and Password are required");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmpId(), loginRequest.getPassword())
            );

            Optional<Employee> employeeOpt = employeeRepository.findByEmpId(loginRequest.getEmpId());

            // ✅ Step 3: Add Debugging Logs
            if (employeeOpt.isPresent()) {
                Employee employee = employeeOpt.get();
                System.out.println("✅ Employee Found: " + employee.getEmpId());
                System.out.println("🔹 Role: " + employee.getRole());
                System.out.println("🔹 Password (Hashed in DB): " + employee.getPassword());

                // ✅ Check if Password Matches
                if (!passwordEncoder.matches(loginRequest.getPassword(), employee.getPassword())) {
                    System.out.println("❌ Incorrect Password for Employee: " + employee.getEmpId());
                    return ResponseEntity.status(401).body("❌ Invalid Password");
                } else {
                    System.out.println("✅ Password Matched for Employee: " + employee.getEmpId());
                }

            } else {
                System.out.println("❌ Employee NOT FOUND in Database: " + loginRequest.getEmpId());
                return ResponseEntity.status(401).body("❌ Invalid Employee ID or Password.");
            }

            Employee employee = employeeOpt.get();
            String role = employee.getRole();

            // ✅ Fix Role Assignment
            boolean isManager = employeeRepository.existsByReportingManagerId(employee.getEmpId());
            boolean isVisaTeam = "VISA_TEAM".equals(employee.getRole());

            if (isManager) {
                role = "MANAGER";
            } else if (isVisaTeam) {
                role = "VISA_TEAM";
            }

            System.out.println("✅ Final Assigned Role: " + role);

            String token = jwtUtil.generateToken(employee.getEmpId());

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", role,
                    "empId", employee.getEmpId()
            ));

        } catch (Exception ex) {
            System.out.println("❌ Exception Occurred During Login: " + ex.getMessage());
            return ResponseEntity.status(401).body("❌ Invalid Employee ID or Password.");
        }
    }
}






