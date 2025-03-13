package com.visa.demo.service;

import com.visa.demo.model.EmployeeDetails;
import com.visa.demo.repository.EmployeeDetailsRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class EmployeeDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailsService.class);
    private final EmployeeDetailsRepository employeeDetailsRepository;

    public EmployeeDetailsService(@Lazy EmployeeDetailsRepository employeeDetailsRepository) {
        this.employeeDetailsRepository = employeeDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String empId) throws UsernameNotFoundException {
        Optional<EmployeeDetails> employeeOpt = employeeDetailsRepository.findByEmpId(empId);

        if (employeeOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        EmployeeDetails employee = employeeOpt.get();

        if (employee.getReportingManager() == null) {
            logger.info("🔹 Employee {} is a MANAGER (rm_id is NULL)", empId);
        } else {
            logger.info("🔹 Employee {} is an EMPLOYEE (rm_id = {})", empId, employee.getReportingManager().getEmpId());
        }

        String role = (employee.getReportingManager()!= null) ? "MANAGER" : "EMPLOYEE";

        return User.withUsername(employee.getEmpId())
                .password(employee.getPassword()) // ✅ Return actual hashed password
                .authorities(role)
                .build();
    }

    // ✅ Add method to hash password before saving employees

}


