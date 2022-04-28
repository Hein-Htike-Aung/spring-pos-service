package com.example.posservice.mapper;

import com.example.posservice.dto.ProductPhotoDto;
import com.example.posservice.dto.ProductPropDto;
import com.example.posservice.dto.ProductRequest;
import com.example.posservice.dto.ProductResponse;
import com.example.posservice.model.Product;
import com.example.posservice.model.ProductPhotos;
import com.example.posservice.model.ProductProp;
import com.example.posservice.repo.repo_provider.BrandRepoProvider;
import com.example.posservice.repo.repo_provider.SubCategoryRepoProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Builder
public class ProductMapper {

    private final BrandRepoProvider brandRepoProvider;
    private final SubCategoryRepoProvider subCategoryRepoProvider;
    private final BrandMapper brandMapper;
    private final SubCategoryMapper subCategoryMapper;

    public ProductResponse mapToDto(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .brandDto(this.brandMapper.mapToDto(product.getBrand()))
                .subCategoryDto(this.subCategoryMapper.mapToDto(product.getSubCategory()))
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    public ProductPhotos mapToProductPhotos(ProductPhotoDto productPhotoDto) {

        return ProductPhotos.builder()
                .photo(productPhotoDto.getPhoto())
                .build();
    }

    public ProductPhotoDto mapToProductPhotosDto(ProductPhotos productPhotos) {
        return ProductPhotoDto.builder()
                .id(productPhotos.getId())
                .productId(productPhotos.getProduct().getId())
                .photo(productPhotos.getPhoto())
                .build();
    }

    public Product mapToProduct(ProductRequest productRequest) {
        return Product.builder()
                .id(productRequest.getId())
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .brand(this.brandRepoProvider.findById(productRequest.getBrandId()))
                .subCategory(this.subCategoryRepoProvider.findById(productRequest.getSubCategoryId()))
                .build();
    }

    public ProductProp mapToProductProperties(ProductPropDto productPropDto) {
        return ProductProp.builder()
                .propertyName(productPropDto.getPropertyName())
                .propertyValue(productPropDto.getPropertyValue())
                .build();
    }

    public ProductPropDto mapToProductPropertiesDto(ProductProp productProp) {
        return ProductPropDto.builder()
                .id(productProp.getId())
                .productId(productProp.getProduct().getId())
                .propertyName(productProp.getPropertyName())
                .propertyValue(productProp.getPropertyValue())
                .build();
    }
}
