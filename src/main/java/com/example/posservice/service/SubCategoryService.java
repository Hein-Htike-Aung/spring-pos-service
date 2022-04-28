package com.example.posservice.service;

import com.example.posservice.dto.SubCategoryDto;
import com.example.posservice.exception.SpringPosException;
import com.example.posservice.mapper.SubCategoryMapper;
import com.example.posservice.model.Product;
import com.example.posservice.model.SubCategory;
import com.example.posservice.repo.SubCategoryRepo;
import com.example.posservice.repo.repo_provider.ProductRepoProvider;
import com.example.posservice.repo.repo_provider.SubCategoryRepoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubCategoryService {

    private final SubCategoryRepo subCategoryRepo;
    private final SubCategoryRepoProvider subCategoryRepoProvider;
    private final ProductRepoProvider productRepoProvider;
    private final SubCategoryMapper subCategoryMapper;

    public List<SubCategoryDto> getAll() {
        return this.subCategoryRepo.findAll().stream()
                .map(subCategoryMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public SubCategoryDto getById(Long id) {
        return subCategoryMapper.mapToDto(findSubCategoryById(id));
    }

    public SubCategory findSubCategoryById(Long id) {
        return this.subCategoryRepoProvider.findById(id);
    }

    public void save(SubCategoryDto subCategoryDto) {
        if (!isExisted(subCategoryDto.getSubCategoryName())) {
            this.subCategoryRepo.save(subCategoryMapper.mapToSubCategory(subCategoryDto));
        } else {
            throw new SpringPosException("Already Exists");
        }
    }

    public void saveList(Collection<SubCategoryDto> subCategoryDtoCollection) {
        Set<SubCategoryDto> subCategoryDtoSet = new HashSet<>(subCategoryDtoCollection);

        subCategoryDtoSet.removeIf(value -> isExisted(value.getSubCategoryName()));

        if (subCategoryDtoSet.size() != 0) {
            this.subCategoryRepo.saveAll(subCategoryDtoSet
                    .stream().map(this.subCategoryMapper::mapToSubCategory).collect(Collectors.toList()));
        }

    }

    private boolean isExisted(String name) {
        return this.subCategoryRepo.findBySubCategoryName(name).isPresent();
    }

    public List<SubCategoryDto> findAllSubCategoriesByCategoryId(Long categoryId) {
        return this.subCategoryRepoProvider.findSubCategoryByCategoryId(categoryId).stream()
                .map(subCategoryMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long subCategoryId) {
        if (isDeletable(subCategoryId)) {
            this.subCategoryRepo.deleteById(subCategoryId);
        } else {
            throw new SpringPosException("This Subcategory cannot be deleted");
        }
    }

    private boolean isDeletable(Long subCategoryId) {
        List<Product> productsBySubCategoryId =
                this.productRepoProvider.findProductBySubCategoryId(subCategoryId);
        return productsBySubCategoryId.isEmpty();
    }

}
