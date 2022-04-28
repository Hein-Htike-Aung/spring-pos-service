package com.example.posservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsResponse {
    private Long id;
    private ProductResponse productResponse;
    private int quantity;
}
