package com.example.posservice.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "CategoryName must not be empty")
    @Column(unique = true)
    private String categoryName;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<SubCategory> subCategories = new HashSet<>();
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Brand> brands = new HashSet<>();

}
