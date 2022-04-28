package com.example.posservice.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "SubCategory Name must not be empty")
    private String subCategoryName;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

}
