package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.Purchase;
import com.example.posservice.repo.PurchaseRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PurchaseRepoProvider {

    private final PurchaseRepo purchaseRepo;

    public List<Purchase> findPurchaseBySupplierId(Long supplierId) {
        return this.purchaseRepo.findBySupplierId(supplierId)
                .orElseThrow(() -> new SpringPosException("No Purchase found with supplierId - " + supplierId));
    }

    public Purchase findById(Long id) {
        return this.purchaseRepo.findById(id)
                .orElseThrow(() -> new SpringPosException("No Purchase found with id - " + id));
    }
}
