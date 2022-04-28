package com.example.posservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PurchaseResponse {
    private Long id;
    private String description;
    private Instant purchaseDate;
    private EmployeeDto employeeDto;
    private SupplierDto supplierDto;
    private List<PurchaseDetailsResponse> purchaseDetails;
}
