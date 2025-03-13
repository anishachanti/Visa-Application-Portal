package com.visa.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String empId;
    private String password;

    // ✅ Explicitly defining getters to avoid issues
    public String getEmpId() {
        return empId;
    }

    public String getPassword() {
        return password;
    }
}


