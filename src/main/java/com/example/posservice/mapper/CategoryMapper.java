package com.example.posservice.mapper;

import com.example.posservice.dto.CategoryDto;
import com.example.posservice.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
public class CategoryMapper {

    public CategoryDto mapToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }

    public Category mapToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .categoryName(categoryDto.getCategoryName())
                .build();
    }
}
