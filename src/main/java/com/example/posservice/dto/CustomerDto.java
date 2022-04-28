package com.example.posservice.dto;

import com.example.posservice.enumeration.Gender;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private String username;
    private String password;
    private boolean enabled;

}
