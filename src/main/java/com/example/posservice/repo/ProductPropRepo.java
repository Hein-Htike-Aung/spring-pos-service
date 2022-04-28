package com.example.posservice.repo;

import com.example.posservice.dto.ProductPropDto;
import com.example.posservice.model.Product;
import com.example.posservice.model.ProductProp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPropRepo extends JpaRepository<ProductProp, Long> {
    void deleteByProduct(Product product);

    void deleteByProductId(Long productId);

    Optional<List<ProductProp>> findByProductId(Long productId);
}
