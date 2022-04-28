package com.example.posservice.repo;

import com.example.posservice.model.Supplier;
import com.example.posservice.model._User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByOrgName(String orgName);

    Optional<Supplier> findByFullName(String fullName);
}
