package com.example.posservice.dto;

import com.example.posservice.model.ProductPhotos;
import com.example.posservice.model.ProductProp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductResponse {
    private Long id;
    private String productName;
    private double price;
    private int quantity;
    private SubCategoryDto subCategoryDto;
    private BrandDto brandDto;
    private List<ProductPhotoDto> photos;
    private List<ProductPropDto> properties;
}
