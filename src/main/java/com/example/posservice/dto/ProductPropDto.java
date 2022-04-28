package com.example.posservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductPropDto {
    private Long id;
    private String propertyName;
    private String propertyValue;
    private Long productId;
}
