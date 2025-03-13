/*package com.visa.demo.service;

import com.visa.demo.model.Employee;
import com.visa.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class EmployeeService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String empId) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByEmpId(empId);
        if (employee.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        boolean isManager = employeeRepository.existsByReportingManagerId(empId);

        String role = isManager ? "MANAGER" : "EMPLOYEE";

        return User.withUsername(employee.get().getEmpId())  // ✅ FIXED
                .password(employee.get().getPassword())  // ✅ FIXED
                .authorities(role)
                .build();
    }


}

 */




