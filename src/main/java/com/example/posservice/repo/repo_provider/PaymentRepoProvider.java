package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.Payment;
import com.example.posservice.repo.PaymentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentRepoProvider {
    private final PaymentRepo paymentRepo;

    public Payment findById(Long id) {
        return this.paymentRepo.findById(id)
                .orElseThrow(() -> new SpringPosException("No Payment found with  id - " + id));
    }
}
