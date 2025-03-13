package com.visa.demo.model;

import  jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Visa_Details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisaDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visaId;

    private String visaType;
    private String managerApprovalStatus;
    private String visaStatus;
    private String lotteryStatus;
    @Temporal(TemporalType.DATE)
    private Date visaApprovedDate;
    @Temporal(TemporalType.DATE)
    private Date visaExpiryDate;
    private String petitionNumber;

    @OneToOne
    @JoinColumn(name = "passport_no")
    private PassportDetails passport;

    public void setPassport(PassportDetails passport) {
        this.passport = passport;
    }

    public void setManagerApprovalStatus(String managerApprovalStatus) {
        this.managerApprovalStatus = managerApprovalStatus;
    }

    public void setVisaStatus(String visaStatus) {
        this.visaStatus = visaStatus;
    }

    // ✅ Add Getters if missing
    public PassportDetails getPassport() {
        return passport;
    }

    public String getManagerApprovalStatus() {
        return managerApprovalStatus;
    }

    public String getVisaStatus() {
        return visaStatus;
    }

}



