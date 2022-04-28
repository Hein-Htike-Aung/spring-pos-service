package com.example.posservice.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "ProductName cannot be empty")
    @Column(unique = true)
    private String productName;
    @Min(value = 10)
    private double price;
    @Min(value = 0)
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    private SubCategory subCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductProp> properties = new ArrayList<>();
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductPhotos> photos = new ArrayList<>();

}
