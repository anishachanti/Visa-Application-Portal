/*package com.visa.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Employee_Education_Details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long educationId;

    private String educationQualification;
    private int startYear;
    private int endYear;
    private double percentage;
    private String institutionUniversityName;
    private Double masterGPA;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private EmployeeDetails employee;
}
*/





