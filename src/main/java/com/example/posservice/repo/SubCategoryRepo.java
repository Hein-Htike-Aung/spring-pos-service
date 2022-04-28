package com.example.posservice.repo;

import com.example.posservice.model.Product;
import com.example.posservice.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {
    Optional<List<SubCategory>> findByCategoryId(Long categoryId);

    Optional<SubCategory> findBySubCategoryName(String name);
}
