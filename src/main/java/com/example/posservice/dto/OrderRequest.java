package com.example.posservice.dto;

import com.example.posservice.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequest {
    private Long id;
    private String description;
    private CustomerDto customerDto;
    private List<OrderDetailsDto> orderDetails;
    private Payment payment;
}
