package com.visa.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "visa_applications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VisaApplication {

    @Id
    @Column(name = "emp_id", nullable = false, unique = true)
    private String empId;

    @Column(name = "full_name", nullable = false,length = 30)
    private String fullName;

    @Column(name = "surname", nullable = false,length = 30)
    private String surname;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dob", nullable = false)
    private Date dob;

    @Column(name = "passport_number", nullable = false, unique = true,length = 30)
    private String passportNumber;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "visa_type", nullable = false)
    private String visaType;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "passport_issue_date", nullable = false)
    private Date passportIssueDate;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "passport_expiry_date", nullable = false)
    private Date passportExpiryDate;

    @Column(name = "country_of_birth", nullable = false,length = 30)
    private String countryOfBirth;

    @Column(name = "city_of_birth", nullable = false,length = 30)
    private String cityOfBirth;

    @Column(name = "citizenship", nullable = false,length = 30)
    private String citizenship;  // Ensure citizenship is required

    @Column(name = "has_approved_petition")
    private Boolean hasApprovedPetition;

    @Column(name = "approved_petition_number", length = 100)
    private String approvedPetitionNumber;

    // ✅ New Fields for Master's Degree Details
    @Column(name = "has_masters_degree")
    private Boolean hasMastersDegree;

    @Column(name = "university_name", length = 50)
    private String universityName;

    @Column(name = "masters_gpa")
    private Double mastersGPA;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "masters_completion_date")
    private Date mastersCompletionDate;

    @Column(name = "passport_file_path", length = 255)
    private String passportFilePath;

    @Column(name = "degree_file_path", length = 255)
    private String degreeFilePath;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // Default status is PENDING

    // ✅ New Field: Date of Approval
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_approved")
    private LocalDate dateApproved;




    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPassportFilePath() {
        return passportFilePath;
    }

    public void setPassportFilePath(String passportFilePath) {
        this.passportFilePath = passportFilePath;
    }

    public String getDegreeFilePath() {
        return degreeFilePath;
    }

    public void setDegreeFilePath(String degreeFilePath) {
        this.degreeFilePath = degreeFilePath;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getVisaType() {
        return visaType;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public void setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public Boolean getHasApprovedPetition() {
        return hasApprovedPetition;
    }

    public void setHasApprovedPetition(Boolean hasApprovedPetition) {
        this.hasApprovedPetition = hasApprovedPetition;
    }

    public String getApprovedPetitionNumber() {
        return approvedPetitionNumber;
    }

    public void setApprovedPetitionNumber(String approvedPetitionNumber) {
        this.approvedPetitionNumber = approvedPetitionNumber;
    }

    public Boolean getHasMastersDegree() {
        return hasMastersDegree;
    }

    public void setHasMastersDegree(Boolean hasMastersDegree) {
        this.hasMastersDegree = hasMastersDegree;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Double getMastersGPA() {
        return mastersGPA;
    }

    public void setMastersGPA(Double mastersGPA) {
        this.mastersGPA = mastersGPA;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(LocalDate dateApproved) {
        this.dateApproved = dateApproved;
    }





}





