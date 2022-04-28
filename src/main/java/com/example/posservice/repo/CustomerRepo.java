package com.example.posservice.repo;

import com.example.posservice.model.Customer;
import com.example.posservice.model._User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserId(Long userId);
}
