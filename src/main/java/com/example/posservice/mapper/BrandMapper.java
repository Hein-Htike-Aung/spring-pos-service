package com.example.posservice.mapper;

import com.example.posservice.dto.BrandDto;
import com.example.posservice.model.Brand;
import com.example.posservice.repo.repo_provider.CategoryRepoProvider;
import com.example.posservice.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class BrandMapper {

    private final CategoryRepoProvider categoryRepoProvider;

    public BrandDto mapToDto(Brand brand) {
        return BrandDto.builder()
                .id(brand.getId())
                .brandName(brand.getBrandName())
                .categoryId(brand.getCategory().getId())
                .categoryName(brand.getCategory().getCategoryName())
                .build();
    }

    public Brand mapToBrand(BrandDto brandDto) {
        return Brand.builder()
                .id(brandDto.getId())
                .brandName(brandDto.getBrandName())
                .category(this.categoryRepoProvider.findById(brandDto.getCategoryId()))
                .build();
    }
}
