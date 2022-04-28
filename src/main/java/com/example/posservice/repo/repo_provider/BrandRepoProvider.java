package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.Brand;
import com.example.posservice.repo.BrandRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BrandRepoProvider {
    private final BrandRepo brandRepo;

    public List<Brand> findBrandsByCategoryId(Long categoryId) {
        return this.brandRepo.findByCategoryId(categoryId)
                .orElseThrow(() -> new SpringPosException("No Brands found with categoryId - " + categoryId));
    }

    public Brand findBrandByBrandName(String brandName) {
        return this.brandRepo.findByBrandName(brandName)
                .orElseThrow(() -> new SpringPosException("No Brand found with brandName - " + brandName));
    }

    public Brand findById(Long id) {
        return this.brandRepo.findById(id)
                .orElseThrow(() -> new SpringPosException("No Brand found with id - " + id));
    }
}
