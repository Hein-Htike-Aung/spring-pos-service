package com.example.posservice.repo.repo_provider;

import com.example.posservice.dto.ProductResponse;
import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.Product;
import com.example.posservice.repo.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ProductRepoProvider {
    private final ProductRepo productRepo;

    public List<Product> findProductByBrandId(Long brandId) {
        return this.productRepo.findByBrandId(brandId)
                .orElseThrow(() -> new SpringPosException("No Product found with brandId - " + brandId));
    }

    public List<Product> findProductBySubCategoryId(Long subCategoryId) {
        return this.productRepo.findBySubCategoryId(subCategoryId)
                .orElseThrow(() -> new SpringPosException("No Product found with subCategoryId - " + subCategoryId));
    }

    public Product findProductByProductName(String productName) {
        return this.productRepo.findByProductName(productName)
                .orElseThrow(() -> new SpringPosException("No Product with product Name - " + productName));
    }

    public Product findById(Long id) {
        return this.productRepo.findById(id)
                .orElseThrow(() -> new SpringPosException("No Product found with id - " + id));
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return this.productRepo.findByCategoryId(categoryId)
                .orElseThrow(() -> new SpringPosException("No Product List Found with categoryId - " + categoryId));
    }
}
