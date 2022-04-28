package com.example.posservice.controller;

import com.example.posservice.dto.*;
import com.example.posservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.productService.getProductDtoById(id));
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(
            @RequestBody ProductRequest productRequest
    ) {
        this.productService.save(productRequest);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully saved")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable("id") Long id,
            @RequestBody ProductRequest productRequest
    ) {
        productRequest.setId(id);
        this.productService.update(productRequest);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully updated")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }

    @GetMapping("/by-brand/{brandId}")
    public ResponseEntity<List<ProductResponse>> findAllProductsByBrandId(
            @PathVariable("brandId") Long brandId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.productService.findAllProductsByBrandId(brandId));
    }

    @GetMapping("/by-subcategory/{subCategoryId}")
    public ResponseEntity<List<ProductResponse>> findAllProductsBySubCategoryId(
            @PathVariable("subCategoryId") Long subCategoryId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.productService.findAllProductsBySubCategoryId(subCategoryId));
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> findAllProductsByCategoryId(
            @PathVariable("categoryId") Long categoryId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.productService.findAllProductsByCategory(categoryId));
    }

    @GetMapping("/photos/by-product/{productId}")
    public ResponseEntity<List<ProductPhotoDto>> findAllPhotosForProduct(
            @PathVariable("productId") Long productId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.productService.findAllPhotosForProduct(productId));
    }

    @GetMapping("/properties/by-product/{productId}")
    public ResponseEntity<List<ProductPropDto>> findAllPropertiesForProduct(
            @PathVariable("productId") Long productId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.productService.findAllPropertiesForProduct(productId));
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_EMPLOYEE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable("id") Long productId
    ) {

        this.productService.delete(productId);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Successfully deleted")
                .httpStatus(HttpStatus.OK)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build());
    }
}
