package com.example.posservice.dto;

import com.example.posservice.model.Employee;
import com.example.posservice.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PurchaseRequest {

    private Long id;
    private String description;
    private Long supplierId;
    private List<PurchaseDetailsDto> purchaseDetails;
}
