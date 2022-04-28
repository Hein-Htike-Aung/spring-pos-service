package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.SubCategory;
import com.example.posservice.repo.SubCategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SubCategoryRepoProvider {
    private SubCategoryRepo subCategoryRepo;

    public List<SubCategory> findSubCategoryByCategoryId(Long categoryId) {
        return this.subCategoryRepo.findByCategoryId(categoryId)
                .orElseThrow(() -> new SpringPosException("No SubCategoryFound with categoryId - " + categoryId));
    }

    public SubCategory findSubCategoryBySubCategoryName(String name) {
        return this.subCategoryRepo.findBySubCategoryName(name)
                .orElseThrow(() -> new SpringPosException("No SubCategory found with SubCategoryName - " + name));
    }

    public SubCategory findById(Long id) {
        return this.subCategoryRepo.findById(id)
                .orElseThrow(() -> new SpringPosException("No Subcategory found with id - " + id));
    }
}
