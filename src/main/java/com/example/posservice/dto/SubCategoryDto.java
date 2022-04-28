package com.example.posservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryDto {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String subCategoryName;
}
