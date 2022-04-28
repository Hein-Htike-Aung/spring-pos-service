package com.example.posservice.dto;

import com.example.posservice.enumeration.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentDto {

    private Long id;
    private String card;
    private CardType cardType;
    private String expireDate;
    private int safeGuard;
}
