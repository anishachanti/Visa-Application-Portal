/*package com.visa.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Employee_Details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDetails {
    @Id
    private String empId;

    private String firstName;
    private String middleName;
    private String lastName;
    private String designation;
    private String email;
    private String password;

    @Temporal(TemporalType.DATE)
    private Date dob;

    @ManyToOne
    @JoinColumn(name = "rm_id")
    private EmployeeDetails reportingManager;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private PassportDetails passportDetails;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EducationDetails> educationDetails;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeeAddress> addresses;

    // ✅ Ensure getter for empId is present
    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    // ✅ Ensure getter for password exists
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {  // ✅ Add this method if missing
        this.password = password;
    }

    public EmployeeDetails getReportingManager() {
        return reportingManager;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDesignation() {
        return designation;
    }

    public PassportDetails getPassportDetails() {
        return passportDetails;
    }

    // ✅ Add Setter
    public void setPassportDetails(PassportDetails passportDetails) {
        this.passportDetails = passportDetails;
    }



}
*/





