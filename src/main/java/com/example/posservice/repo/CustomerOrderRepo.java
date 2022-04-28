package com.example.posservice.repo;

import com.example.posservice.model.CustomerOrder;
import com.example.posservice.model.PurchaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerOrderRepo extends JpaRepository<CustomerOrder, Long> {

    Optional<List<CustomerOrder>> findByCustomerId(Long customerId);


}
