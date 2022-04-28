package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.Customer;
import com.example.posservice.repo.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerRepoProvider {
    private final CustomerRepo customerRepo;

    public Customer findCustomerByUserId(Long userId) {
        return this.customerRepo.findByUserId(userId)
                .orElseThrow(() -> new SpringPosException("No Customer found with user id " + userId));
    }

    public Customer findById(Long id) {
        return this.customerRepo.findById(id)
                .orElseThrow(() -> new SpringPosException("No Customer found with id - " + id));
    }

}
