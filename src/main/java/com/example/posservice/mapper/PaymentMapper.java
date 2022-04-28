package com.example.posservice.mapper;

import com.example.posservice.dto.PaymentDto;
import com.example.posservice.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Builder
public class PaymentMapper {
    public PaymentDto mapToDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .card(payment.getCard())
                .expireDate(payment.getExpireDate())
                .safeGuard(payment.getSafeGuard())
                .cardType(payment.getCardType())
                .build();
    }
}
