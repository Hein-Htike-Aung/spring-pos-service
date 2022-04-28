package com.example.posservice.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "MEMBER")
public abstract class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    private String fullName;
    @NotEmpty(message = "Phone cannot be empty")
    private String phone;
    private String address;
}
