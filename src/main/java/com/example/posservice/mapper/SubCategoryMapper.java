package com.example.posservice.mapper;

import com.example.posservice.dto.SubCategoryDto;
import com.example.posservice.model.SubCategory;
import com.example.posservice.repo.repo_provider.CategoryRepoProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
public class SubCategoryMapper {

    private final CategoryRepoProvider categoryRepoProvider;

    public SubCategoryDto mapToDto(SubCategory subCategory) {
        return SubCategoryDto.builder()
                .id(subCategory.getId())
                .subCategoryName(subCategory.getSubCategoryName())
                .categoryId(subCategory.getCategory().getId())
                .categoryName(subCategory.getCategory().getCategoryName())
                .build();
    }

    public SubCategory mapToSubCategory(SubCategoryDto subCategoryDto) {

        return SubCategory.builder()
                .id(subCategoryDto.getId())
                .subCategoryName(subCategoryDto.getSubCategoryName())
                .category(this.categoryRepoProvider.findById(subCategoryDto.getCategoryId()))
                .build();
    }
}
