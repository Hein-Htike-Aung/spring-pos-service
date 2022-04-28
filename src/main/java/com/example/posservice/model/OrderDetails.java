package com.example.posservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(0)
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerOrder customerOrder;
}
