package com.example.posservice.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SupplierDto {
    private Long id;
    private String fullName;
    private String phone;
    private String address;
    private String orgName;
}
