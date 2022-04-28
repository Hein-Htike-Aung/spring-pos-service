package com.example.posservice.mapper;

import com.example.posservice.dto.PurchaseDetailsDto;
import com.example.posservice.dto.PurchaseDetailsResponse;
import com.example.posservice.dto.PurchaseRequest;
import com.example.posservice.dto.PurchaseResponse;
import com.example.posservice.model.Purchase;
import com.example.posservice.model.PurchaseDetails;
import com.example.posservice.model._User;
import com.example.posservice.repo.repo_provider.EmployeeRepoProvider;
import com.example.posservice.repo.repo_provider.ProductRepoProvider;
import com.example.posservice.repo.repo_provider.SupplierRepoProvider;
import com.example.posservice.security.CurrentUserHolder;
import com.example.posservice.service.AppUserService;
import com.example.posservice.service.EmployeeService;
import com.example.posservice.service.ProductService;
import com.example.posservice.service.SupplierService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@AllArgsConstructor
@Component
@Builder
public class PurchaseMapper {

    private final EmployeeService employeeService;
    private final AppUserService appUserService;
    private final SupplierService supplierService;
    private final ProductService productService;
    private final EmployeeRepoProvider employeeRepoProvider;
    private final SupplierRepoProvider supplierRepoProvider;
    private final ProductRepoProvider productRepoProvider;
    private final CurrentUserHolder currentUserHolder;

    public Purchase mapToPurchase(PurchaseRequest purchaseRequest) {
        return Purchase.builder()
                .id(purchaseRequest.getId())
                .purchaseDate(Instant.now())
                .description(purchaseRequest.getDescription())
                .employee(this.employeeRepoProvider.findEmployeeByUserId(this.appUserService.getCurrentUser().getId()))
                .supplier(this.supplierRepoProvider.findById(purchaseRequest.getSupplierId()))
                .build();
    }

    public PurchaseDetails mapToPurchaseDetails(PurchaseDetailsDto purchaseDetailsDto) {
        return PurchaseDetails.builder()
                .id(purchaseDetailsDto.getId())
                .product(this.productRepoProvider.findById(purchaseDetailsDto.getProductId()))
                .quantity(purchaseDetailsDto.getQuantity())
                .build();
    }

    public PurchaseResponse mapToPurchaseResponse(Purchase purchase) {
        return PurchaseResponse.builder()
                .id(purchase.getId())
                .description(purchase.getDescription())
                .purchaseDate(purchase.getPurchaseDate())
                .employeeDto(this.employeeService.getEmployeeDtoById(purchase.getEmployee().getId()))
                .supplierDto(this.supplierService.getSupplierDtoById(purchase.getSupplier().getId()))
                .build();
    }

    public PurchaseDetailsResponse mapToPurchaseDetailsResponse(PurchaseDetails purchaseDetails) {
        return PurchaseDetailsResponse.builder()
                .id(purchaseDetails.getId())
                .productResponse(productService.getProductDtoById(purchaseDetails.getProduct().getId()))
                .quantity(purchaseDetails.getQuantity())
                .purchaseResponse(this.mapToPurchaseResponse(purchaseDetails.getPurchase()))
                .build();
    }
}
