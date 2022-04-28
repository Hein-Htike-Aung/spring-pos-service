package com.example.posservice.repo;

import com.example.posservice.dto.ProductResponse;
import com.example.posservice.model.Category;
import com.example.posservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<List<Product>> findByBrandId(Long brandId);

    Optional<List<Product>> findBySubCategoryId(Long subCategoryId);

    Optional<Product> findByProductName(String productName);

    @Query(value = "select p from Product p where p.subCategory.id in " +
            "( select sc.id from SubCategory sc where sc.category.id = " +
            "( select c.id from Category c where c.id = :categoryId))")
    Optional<List<Product>> findByCategoryId(@Param("categoryId")Long categoryId);
}
