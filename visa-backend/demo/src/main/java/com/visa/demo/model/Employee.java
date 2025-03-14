package com.visa.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "employees")
public class Employee {
    @Id
    private String empId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double grade;

    @Column(nullable = true)
    private String reportingManagerId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String email;


    // ✅ Manually defined getters (Fix for missing method error)
    public String getEmpId() {
        return empId;
    }

    public String getPassword() {
        return password;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getReportingManagerId() {
        return reportingManagerId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}





