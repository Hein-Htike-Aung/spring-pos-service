package com.example.posservice.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String brandName;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

}
