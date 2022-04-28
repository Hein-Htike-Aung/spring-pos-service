package com.example.posservice.service;

import com.example.posservice.dto.CategoryDto;
import com.example.posservice.exception.SpringPosException;
import com.example.posservice.mapper.CategoryMapper;
import com.example.posservice.model.Brand;
import com.example.posservice.model.Category;
import com.example.posservice.model.SubCategory;
import com.example.posservice.repo.CategoryRepo;
import com.example.posservice.repo.repo_provider.BrandRepoProvider;
import com.example.posservice.repo.repo_provider.CategoryRepoProvider;
import com.example.posservice.repo.repo_provider.SubCategoryRepoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;
    private final CategoryRepoProvider categoryRepoProvider;
    private final SubCategoryRepoProvider subCategoryRepoProvider;
    private final BrandRepoProvider brandRepoProvider;

    public List<CategoryDto> getAll() {
        return this.categoryRepo.findAll()
                .stream()
                .map(categoryMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public void save(CategoryDto categoryDto) {
        if (!isExisted(categoryDto.getCategoryName())) {
            this.categoryRepo.save(categoryMapper.mapToCategory(categoryDto));
        } else {
            throw new SpringPosException("Category Already Exists");
        }
    }

    private boolean isExisted(String categoryName) {
        return this.categoryRepo.findByCategoryName(categoryName).isPresent();
    }

    public CategoryDto findDtoById(Long id) {
        return categoryMapper.mapToDto(findById(id));
    }

    public Category findById(Long id) {
        return this.categoryRepoProvider.findById(id);
    }

    public void deleteById(Long categoryId) {
        if (deletable(categoryId)) {
            this.categoryRepo.deleteById(categoryId);
        } else {
            throw new SpringPosException("This Category cannot be deleted");
        }
    }

    private boolean deletable(Long categoryId) {
        List<SubCategory> subCategoryByCategoryId = this.subCategoryRepoProvider
                .findSubCategoryByCategoryId(categoryId);
        List<Brand> brandByCategoryId = this.brandRepoProvider.findBrandsByCategoryId(categoryId);

        return subCategoryByCategoryId.isEmpty() || brandByCategoryId.isEmpty();
    }
}
