package com.example.posservice.service;

import com.example.posservice.dto.PurchaseDetailsResponse;
import com.example.posservice.dto.PurchaseRequest;
import com.example.posservice.dto.PurchaseResponse;
import com.example.posservice.mapper.PurchaseMapper;
import com.example.posservice.model.Product;
import com.example.posservice.model.Purchase;
import com.example.posservice.model.PurchaseDetails;
import com.example.posservice.repo.ProductRepo;
import com.example.posservice.repo.PurchaseDetailsRepo;
import com.example.posservice.repo.PurchaseRepo;
import com.example.posservice.repo.repo_provider.ProductRepoProvider;
import com.example.posservice.repo.repo_provider.PurchaseDetailsRepoProvider;
import com.example.posservice.repo.repo_provider.PurchaseRepoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseService {

    private final PurchaseRepo purchaseRepo;
    private final ProductRepo productRepo;
    private final ProductRepoProvider productRepoProvider;
    private final PurchaseDetailsRepo purchaseDetailsRepo;
    private final PurchaseRepoProvider purchaseRepoProvider;
    private final PurchaseDetailsRepoProvider purchaseDetailsRepoProvider;
    private final PurchaseMapper purchaseMapper;

    public void save(PurchaseRequest purchaseRequest) {
        Purchase savedPurchase = this.purchaseRepo
                .save(this.purchaseMapper.mapToPurchase(purchaseRequest));

        this.purchaseDetailsRepo.saveAll(
                purchaseRequest.getPurchaseDetails().stream()
                        .map(this.purchaseMapper::mapToPurchaseDetails)
                        .peek(purchaseDetails -> purchaseDetails.setPurchase(savedPurchase))
                        .collect(Collectors.toList()));

        // Update Product Quantity
        purchaseRequest.getPurchaseDetails().forEach(pd -> {
            Product product = this.productRepoProvider.findById(pd.getProductId());
            product.setQuantity(product.getQuantity() + pd.getQuantity());

            this.productRepo.save(product);
        });
    }

    public List<PurchaseResponse> getAll() {
        return this.purchaseRepo.findAll().stream()
                .map(this.purchaseMapper::mapToPurchaseResponse)
                .collect(Collectors.toList());
    }

    public List<PurchaseDetailsResponse> getPurchaseDetailsForPurchase(Long purchaseId) {
        return this.purchaseDetailsRepoProvider.findPurchaseDetailsListByPurchaseId(purchaseId).stream()
                .map(this.purchaseMapper::mapToPurchaseDetailsResponse)
                .collect(Collectors.toList());
    }

    public PurchaseResponse getById(Long id) {
        return this.purchaseMapper.mapToPurchaseResponse(this.getPurchaseById(id));
    }

    public Purchase getPurchaseById(Long purchaseId) {
        return this.purchaseRepoProvider.findById(purchaseId);
    }

    public Long getCurrentMonthExpenses() {
        long total;
        List<PurchaseDetails> monthlyPurchaseDetails = this.purchaseDetailsRepoProvider.findCurrentMonthPurchaseDetails();

        total = monthlyPurchaseDetails.stream()
                .mapToLong(od -> (long) (od.getProduct().getPrice() * od.getQuantity())).sum();

        return total;
    }

    public List<Long> getMonthlyExpenses() {

        String[] months = {
                "2022-01-01",
                "2022-02-01",
                "2022-03-01",
                "2022-04-01",
                "2022-05-01",
                "2022-06-01",
                "2022-07-01",
                "2022-08-01",
                "2022-09-01",
                "2022-10-01",
                "2022-11-01",
                "2022-12-01",
        };

        List<Long> monthlyIncomes = new ArrayList<>();

        Arrays.stream(months).forEach(month -> monthlyIncomes.add(
                this.purchaseDetailsRepoProvider.findPurchaseDetailsByMonth(Date.valueOf(month)).stream()
                        .mapToLong(od -> (long) (od.getProduct().getPrice() * od.getQuantity())).sum()
        ));

        return monthlyIncomes;

    }
}
