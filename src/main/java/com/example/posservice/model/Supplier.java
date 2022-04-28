package com.example.posservice.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance
@DiscriminatorValue("SUPPLIER")
public class Supplier extends Member{

    @NotEmpty(message = "Organization Name must not be empty")
    @Column(unique = true)
    private String orgName;
}
