package com.example.posservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {
    private Long id;
    private String brandName;
    private Long categoryId;
    private String categoryName;
}
