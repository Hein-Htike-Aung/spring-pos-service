package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.PurchaseDetails;
import com.example.posservice.repo.PurchaseDetailsRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class PurchaseDetailsRepoProvider {
    private final PurchaseDetailsRepo purchaseDetailsRepo;

    public List<PurchaseDetails> findPurchaseDetailsListByProductId(Long productId) {
        return this.purchaseDetailsRepo.findByProductId(productId)
                .orElseThrow(() -> new SpringPosException("No Purchase Details List found with productId - " + productId));
    }

    public List<PurchaseDetails> findPurchaseDetailsListByPurchaseId(Long purchaseId) {
        return this.purchaseDetailsRepo.findByPurchaseId(purchaseId)
                .orElseThrow(() -> new SpringPosException("No Purchase Details List found with purchaseId - " + purchaseId));
    }
    public List<PurchaseDetails> findCurrentMonthPurchaseDetails() {
        return this.purchaseDetailsRepo.findCurrentMonthPurchaseDetails()
                .orElseThrow(() -> new SpringPosException("There is no Monthly Expenses"));

    }

    public List<PurchaseDetails> findPurchaseDetailsByMonth(@Param("date") Date date) {
        return this.purchaseDetailsRepo.findPurchaseDetailsByMonth(date).orElse(List.of());
    }


}
