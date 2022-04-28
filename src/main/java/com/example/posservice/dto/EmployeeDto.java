package com.example.posservice.dto;

import com.example.posservice.enumeration.Gender;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    private Long id;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String email;
    private boolean enabled;
    private String fullName;
    private String phone;
    private String address;
    private Instant dob;
    private String maritalStatus;
    private Gender gender;
}
