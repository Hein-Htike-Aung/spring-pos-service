package com.example.posservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductRequest {
    private Long id;
    private String productName;
    private double price;
    private int quantity;
    private Long subCategoryId;
    private Long brandId;
    private String brandName;
    private List<ProductPhotoDto> photos;
    private List<ProductPropDto> properties;
}
