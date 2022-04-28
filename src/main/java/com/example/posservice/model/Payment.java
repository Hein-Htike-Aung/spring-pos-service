package com.example.posservice.model;

import com.example.posservice.enumeration.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String card;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @NotEmpty
    private String expireDate;
    @Min(100)
    @Max(999)
    private int safeGuard;
}
