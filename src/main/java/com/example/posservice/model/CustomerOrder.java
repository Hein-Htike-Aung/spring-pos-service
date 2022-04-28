package com.example.posservice.model;

import com.example.posservice.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant deliveryDate;
    @NotNull
    private Instant orderDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToOne(fetch = FetchType.LAZY)
    private Customer customer;
    @OneToOne(fetch = FetchType.LAZY)
    private Employee employee;
    @OneToMany(mappedBy = "customerOrder")
    private List<OrderDetails> orderDetailsList = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    private Payment payment;
    private String description;
}
