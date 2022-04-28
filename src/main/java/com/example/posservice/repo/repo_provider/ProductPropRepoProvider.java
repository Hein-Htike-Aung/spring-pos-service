package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.ProductProp;
import com.example.posservice.repo.ProductPropRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ProductPropRepoProvider {
    private ProductPropRepo productPropRepo;

    public List<ProductProp> findProductPropertyListByProductId(Long productId) {
        return this.productPropRepo.findByProductId(productId)
                .orElseThrow(() -> new SpringPosException("No Product Property List found with productId - " + productId));
    }
}
