package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.ProductPhotos;
import com.example.posservice.repo.ProductPhotosRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ProductPhotosRepoProvider {
    private ProductPhotosRepo productPhotosRepo;

    public List<ProductPhotos> findProductPhotosListByProductId(Long productId) {
        return this.productPhotosRepo.findByProductId(productId)
                .orElseThrow(() -> new SpringPosException("No ProductPhotos List found with productId - " + productId));
    }
}
