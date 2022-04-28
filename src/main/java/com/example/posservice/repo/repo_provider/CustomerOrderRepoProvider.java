package com.example.posservice.repo.repo_provider;

import com.example.posservice.dto.OrderResponse;
import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.CustomerOrder;
import com.example.posservice.repo.CustomerOrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CustomerOrderRepoProvider {
    private final CustomerOrderRepo customerOrderRepo;

    public CustomerOrder findById(Long id) {
        return this.customerOrderRepo.findById(id)
                .orElseThrow(() -> new SpringPosException("No CustomerOrder Found with id - " + id));
    }

    public List<CustomerOrder> findByCustomerId(Long customerId) {
        return this.customerOrderRepo.findByCustomerId(customerId)
                .orElseThrow(() -> new SpringPosException("No CustomerOrder Found with customerId - " + customerId));
    }
}
