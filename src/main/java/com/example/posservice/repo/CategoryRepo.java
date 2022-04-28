package com.example.posservice.repo;

import com.example.posservice.model.Brand;
import com.example.posservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);
}
