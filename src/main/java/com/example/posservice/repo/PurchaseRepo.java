package com.example.posservice.repo;

import com.example.posservice.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Long> {
    Optional<List<Purchase>> findBySupplierId(Long supplierId);
}
