package com.example.posservice.dto;

import com.example.posservice.model.Product;
import com.example.posservice.model.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetailsDto {
    private Long id;
    private Long productId;
    private int quantity;
}
