package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.Category;
import com.example.posservice.repo.CategoryRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryRepoProvider {
    private final CategoryRepo categoryRepo;

    public Category findCategoryByCategoryName(String categoryName) {
        return this.categoryRepo.findByCategoryName(categoryName)
                .orElseThrow(() -> new SpringPosException("No Category found with categoryName - " + categoryName));
    }

    public Category findById(Long id){
        return this.categoryRepo.findById(id)
                .orElseThrow(() -> new SpringPosException("No Category found with id - " + id));
    }
}
