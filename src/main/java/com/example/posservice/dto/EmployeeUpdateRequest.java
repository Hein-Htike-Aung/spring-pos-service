package com.example.posservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeUpdateRequest {
    private Long employeeId;
    private String email;
    private String phone;
    private String address;
    private String maritalStatus;

}
