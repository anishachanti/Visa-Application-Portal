/*package com.visa.demo.model;

import jakarta.persistence.*;
import lombok.*;

 @Entity
@Table(name = "Employee_Address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String addressType;
    private String city;
    private String state;
    private String country;
    private String village;
    private String street;
    private String doorNo;
    private String landMark;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private EmployeeDetails employee;

     public void setEmployee(EmployeeDetails employee) {
         this.employee = employee;
     }

     // ✅ Explicitly define missing getters
     public String getAddressType() { return addressType; }
     public String getCity() { return city; }
     public String getState() { return state; }
     public String getCountry() { return country; }
     public String getVillage() { return village; }
     public String getStreet() { return street; }
     public String getDoorNo() { return doorNo; }
     public String getLandMark() { return landMark; }
     public void setAddressType(String addressType) { this.addressType = addressType; }
     public void setCity(String city) { this.city = city; }
     public void setState(String state) { this.state = state; }
     public void setCountry(String country) { this.country = country; }
     public void setVillage(String village) { this.village = village; }
     public void setStreet(String street) { this.street = street; }
     public void setDoorNo(String doorNo) { this.doorNo = doorNo; }
     public void setLandMark(String landMark) { this.landMark = landMark; }
}
*/




