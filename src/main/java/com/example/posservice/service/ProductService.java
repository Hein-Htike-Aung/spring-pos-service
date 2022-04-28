package com.example.posservice.service;

import com.example.posservice.dto.ProductPhotoDto;
import com.example.posservice.dto.ProductPropDto;
import com.example.posservice.dto.ProductRequest;
import com.example.posservice.dto.ProductResponse;
import com.example.posservice.exception.SpringPosException;
import com.example.posservice.mapper.ProductMapper;
import com.example.posservice.model.OrderDetails;
import com.example.posservice.model.Product;
import com.example.posservice.model.PurchaseDetails;
import com.example.posservice.repo.ProductPhotosRepo;
import com.example.posservice.repo.ProductPropRepo;
import com.example.posservice.repo.ProductRepo;
import com.example.posservice.repo.repo_provider.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductPhotosRepo productPhotosRepo;
    private final ProductPropRepo productPropRepo;
    private final ProductRepo productRepo;
    private final ProductPhotosRepoProvider productPhotosRepoProvider;
    private final ProductPropRepoProvider productPropRepoProvider;
    private final ProductRepoProvider productRepoProvider;
    private final PurchaseDetailsRepoProvider purchaseDetailsRepoProvider;
    private final OrderDetailsRepoProvider orderDetailsRepoProvider;
    private final ProductMapper productMapper;

    public List<ProductResponse> getAll() {
        return this.productRepo.findAll().stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public void save(ProductRequest productRequest) {
        if (!isExistedByProductName(productRequest.getProductName())) {

            Product product = this.productMapper.mapToProduct(productRequest);
            this.saveProduct_photos_properties(product, productRequest);

        } else {
            throw new SpringPosException("Already Exits");
        }
    }

    public void update(ProductRequest productRequest) {
        Product product = this.productMapper.mapToProduct(productRequest);

        this.productPropRepo.deleteByProduct(product);
        this.productPhotosRepo.deleteByProduct(product);

        this.saveProduct_photos_properties(product, productRequest);

    }

    private void saveProduct_photos_properties(Product p, ProductRequest productRequest) {
        // Need to Insert Product Table First
        Product savedProduct = this.productRepo.save(p);

        this.productPhotosRepo.saveAll(
                productRequest.getPhotos().stream()
                        .map(productMapper::mapToProductPhotos)
                        .peek(productPhotos -> productPhotos.setProduct(savedProduct))
                        .collect(Collectors.toList())
        );

        this.productPropRepo.saveAll(
                productRequest.getProperties().stream()
                        .map(productMapper::mapToProductProperties)
                        .peek(productProp -> productProp.setProduct(savedProduct))
                        .collect(Collectors.toList())
        );
    }

    private boolean isExistedByProductName(String productName) {
        return this.productRepo.findByProductName(productName).isPresent();
    }

    public List<ProductResponse> findAllProductsByBrandId(Long brandId) {
        return this.productRepoProvider.findProductByBrandId(brandId).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findAllProductsBySubCategoryId(Long subCategoryId) {
        return this.productRepoProvider.findProductBySubCategoryId(subCategoryId).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ProductPhotoDto> findAllPhotosForProduct(Long productId) {
        return this.productPhotosRepoProvider.findProductPhotosListByProductId(productId).stream()
                .map(this.productMapper::mapToProductPhotosDto)
                .collect(Collectors.toList());
    }

    public List<ProductPropDto> findAllPropertiesForProduct(Long productId) {
        return this.productPropRepoProvider.findProductPropertyListByProductId(productId).stream()
                .map(this.productMapper::mapToProductPropertiesDto)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductDtoById(Long id) {
        return this.productMapper.mapToDto(getProductById(id));
    }

    public Product getProductById(Long id) {
        return this.productRepoProvider.findById(id);
    }

    public void delete(Long productId) {
        if (isDeletable(productId)) {

            this.productPhotosRepo.deleteByProductId(productId);
            this.productPropRepo.deleteByProductId(productId);

            this.productRepo.deleteById(productId);
        }else {
            throw new SpringPosException("This product cannot be deleted");
        }
    }

    private boolean isDeletable(Long productId) {
        List<PurchaseDetails> purchaseDetailsByProductId =
                this.purchaseDetailsRepoProvider.findPurchaseDetailsListByProductId(productId);
        List<OrderDetails> orderDetailsByProductId =
                this.orderDetailsRepoProvider.findOrderDetailsListByProductId(productId);

        return purchaseDetailsByProductId.isEmpty() && orderDetailsByProductId.isEmpty();
    }

    public List<ProductResponse> findAllProductsByCategory(Long categoryId) {
        return this.productRepoProvider.findByCategoryId(categoryId).stream()
                .map(this.productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public Long productTotalQuantitiesByCategory(Long categoryId) {
        long total;
        List<Product> products = this.productRepoProvider.findByCategoryId(categoryId);

        total = products.stream().mapToLong(Product::getQuantity).sum();

        return total;
    }
}
