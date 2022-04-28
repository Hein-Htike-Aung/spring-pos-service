package com.example.posservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;
    @Min(1)
    private int quantity;
    @ManyToOne
    private Purchase purchase;
}
