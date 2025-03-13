package com.visa.demo.controller;

import com.visa.demo.model.EmployeeAddress;
import com.visa.demo.model.EmployeeDetails;
import com.visa.demo.repository.EmployeeAddressRepository;
import com.visa.demo.repository.EmployeeDetailsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/address")
public class EmployeeAddressController {
    private final EmployeeAddressRepository addressRepository;
    private final EmployeeDetailsRepository employeeRepository;

    public EmployeeAddressController(EmployeeAddressRepository addressRepository, EmployeeDetailsRepository employeeRepository) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
    }

    // ✅ Add Employee Address
    @PostMapping("/add/{empId}")
    public ResponseEntity<?> addAddress(@PathVariable String empId, @RequestBody EmployeeAddress address) {
        Optional<EmployeeDetails> employeeOpt = employeeRepository.findById(empId);

        if (employeeOpt.isPresent()) {
            address.setEmployee(employeeOpt.get());
            addressRepository.save(address);
            return ResponseEntity.ok("Address added successfully.");
        } else {
            return ResponseEntity.status(404).body("Employee not found.");
        }
    }

    // ✅ Get Addresses for an Employee
    @GetMapping("/employee/{empId}")
    public ResponseEntity<?> getEmployeeAddresses(@PathVariable String empId) {
        List<EmployeeAddress> addresses = addressRepository.findByEmployee_EmpId(empId);

        if (addresses.isEmpty()) {
            return ResponseEntity.status(404).body("No addresses found for this employee.");
        }
        return ResponseEntity.ok(addresses);
    }

    // ✅ Update Employee Address
    @PutMapping("/update/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable Long addressId, @RequestBody EmployeeAddress updatedAddress) {
        Optional<EmployeeAddress> addressOpt = addressRepository.findById(addressId);

        if (addressOpt.isPresent()) {
            EmployeeAddress address = addressOpt.get();
            address.setAddressType(updatedAddress.getAddressType());
            address.setCity(updatedAddress.getCity());
            address.setState(updatedAddress.getState());
            address.setCountry(updatedAddress.getCountry());
            address.setVillage(updatedAddress.getVillage());
            address.setStreet(updatedAddress.getStreet());
            address.setDoorNo(updatedAddress.getDoorNo());
            address.setLandMark(updatedAddress.getLandMark());

            addressRepository.save(address);
            return ResponseEntity.ok("Address updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Address not found.");
        }
    }

    // ✅ Delete Employee Address
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        if (addressRepository.existsById(addressId)) {
            addressRepository.deleteById(addressId);
            return ResponseEntity.ok("Address deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Address not found.");
        }
    }
}


