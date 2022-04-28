package com.example.posservice.repo;

import com.example.posservice.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepo extends JpaRepository<Brand, Long> {
    Optional<List<Brand>> findByCategoryId(Long categoryId);

    Optional<Brand> findByBrandName(String brandName);
}
