package com.example.posservice.dto;

import com.example.posservice.model.Product;
import com.example.posservice.model.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetailsResponse {
    private Long id;
    private ProductResponse productResponse;
    private int quantity;
    private PurchaseResponse purchaseResponse;
}
