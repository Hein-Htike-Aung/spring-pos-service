package com.example.posservice.controller;

import com.example.posservice.dto.*;
import com.example.posservice.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/purchase")
@AllArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(
            @RequestBody PurchaseRequest purchaseRequest
    ) {
        this.purchaseService.save(purchaseRequest);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully created")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @GetMapping("/list")
    public ResponseEntity<List<PurchaseResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.purchaseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> getById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.purchaseService.getById(id));
    }

    @GetMapping("/purchase-details/by-purchase/{id}")
    public ResponseEntity<List<PurchaseDetailsResponse>> getPurchaseDetailsForPurchase(
            @PathVariable("id") Long purchaseId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.purchaseService.getPurchaseDetailsForPurchase(purchaseId));
    }
}
