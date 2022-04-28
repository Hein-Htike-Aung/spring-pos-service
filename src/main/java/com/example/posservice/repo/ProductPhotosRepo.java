package com.example.posservice.repo;

import com.example.posservice.model.Product;
import com.example.posservice.model.ProductPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPhotosRepo extends JpaRepository<ProductPhotos, Long> {
    void deleteByProduct(Product product);

    void deleteByProductId(Long productId);

    Optional<List<ProductPhotos>> findByProductId(Long productId);
}
