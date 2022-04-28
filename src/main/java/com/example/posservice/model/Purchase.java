package com.example.posservice.model;

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
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @NotNull
    private Instant purchaseDate;
    @OneToOne(fetch = FetchType.LAZY)
    private Employee employee;
    @OneToOne(fetch = FetchType.LAZY)
    private Supplier supplier;
    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY)
    private List<PurchaseDetails> purchaseDetails = new ArrayList<>();
}
