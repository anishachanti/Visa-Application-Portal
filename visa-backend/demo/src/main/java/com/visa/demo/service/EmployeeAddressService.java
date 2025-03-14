/*package com.visa.demo.service;

import com.visa.demo.model.EmployeeAddress;
import com.visa.demo.repository.EmployeeAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeAddressService {

    @Autowired
    private EmployeeAddressRepository employeeAddressRepository;

    // Save an employee address
    public EmployeeAddress saveAddress(EmployeeAddress address) {
        return employeeAddressRepository.save(address);
    }

    // Get all addresses for an employee
    public List<EmployeeAddress> getAddressesByEmployeeId(String empId) {
        return employeeAddressRepository.findByEmployee_EmpId(empId);
    }
}
*/



