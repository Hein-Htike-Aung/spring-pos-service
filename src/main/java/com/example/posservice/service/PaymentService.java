package com.example.posservice.service;

import com.example.posservice.dto.PaymentDto;
import com.example.posservice.mapper.PaymentMapper;
import com.example.posservice.repo.PaymentRepo;
import com.example.posservice.repo.repo_provider.PaymentRepoProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final PaymentRepoProvider paymentRepoProvider;
    private final PaymentMapper paymentMapper;

    public PaymentDto getPaymentDtoById(Long id) {
        return this.paymentMapper.mapToDto(this.paymentRepoProvider.findById(id));
    }
}
