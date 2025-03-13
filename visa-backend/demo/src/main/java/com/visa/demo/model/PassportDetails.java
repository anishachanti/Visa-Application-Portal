package com.visa.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Passport_Details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassportDetails {
    @Id
    private String passportNo;
    private String nationality;
    @Temporal(TemporalType.DATE)
    private Date passportIssuedDate;
    @Temporal(TemporalType.DATE)
    private Date passportExpiryDate;
    private String countryOfBirth;
    private String cityOfBirth;
    private String citizenship;

    @OneToOne
    @JoinColumn(name = "emp_id")
    private EmployeeDetails employee;

    @OneToOne(mappedBy = "passport", cascade = CascadeType.ALL)
    private VisaDetails visaDetails;  // ✅ Corrected field

    // ✅ Corrected Getter
    public VisaDetails getVisaDetails() {
        return visaDetails;
    }

    // ✅ Corrected Setter
    public void setVisaDetails(VisaDetails visaDetails) {
        this.visaDetails = visaDetails;
    }
}




