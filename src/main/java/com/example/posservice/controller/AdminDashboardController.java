package com.example.posservice.controller;

import com.example.posservice.dto.ApiResponse;
import com.example.posservice.service.CustomerOrderService;
import com.example.posservice.service.ProductService;
import com.example.posservice.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/dashboard")
public class AdminDashboardController {
    private final CustomerOrderService customerOrderService;
    private final PurchaseService purchaseService;
    private final ProductService productService;

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/current-month/incomes")
    public ResponseEntity<ApiResponse> getMonthlyIncomes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .data(Map.of("income", this.customerOrderService.getCurrentMonthIncome()))
                        .message("Current Month Income")
                        .httpStatus(HttpStatus.OK)
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/monthly/incomes")
    public ResponseEntity<ApiResponse> getIncomesForEachMonth() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .data(Map.of("incomes", this.customerOrderService.getMonthlyIncomes()))
                        .message("Monthly Incomes")
                        .httpStatus(HttpStatus.OK)
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/current-month/expenses")
    public ResponseEntity<ApiResponse> getMonthlyExpenses() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .data(Map.of("expense", this.purchaseService.getCurrentMonthExpenses()))
                        .message("Current Month Expense")
                        .httpStatus(HttpStatus.OK)
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/monthly/expenses")
    public ResponseEntity<ApiResponse> getExpensesForEachMonth() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .data(Map.of("expenses", this.purchaseService.getMonthlyExpenses()))
                        .message("Monthly Expenses")
                        .httpStatus(HttpStatus.OK)
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/product-quantity/by-category/{categoryId}")
    public ResponseEntity<ApiResponse> ProductTotalQuantitiesByCategory(
            @PathVariable("categoryId") Long categoryId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .data(Map.of("quantity", this.productService.productTotalQuantitiesByCategory(categoryId)))
                        .message("Total Product's Quantity")
                        .httpStatus(HttpStatus.OK)
                        .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build());
    }
}
